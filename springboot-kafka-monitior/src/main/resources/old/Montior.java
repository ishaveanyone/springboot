/**
 * Date: 2021-02-20 18:23
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior.old;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
//使用生产者拦截器 监控 队列的 阻塞情况
@Deprecated
public class Montior implements ProducerInterceptor {

    static Integer count =0;

    //如果发送的消息超过了线程数 会分批次进行onsend 比如 如果100个消息 10个线程 那么会分10个批次 进行执行onsend 然后执行 onAcknowledgement
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        Long start=System.currentTimeMillis();
        Map<Integer,Long> topicPartitionData  =  montiorTopicMessage(producerRecord.topic());

        //分析对应的比例如果比例比较大 那么久需要进行性重新定义分配
        int targetPartition =0;
        long restOffset=topicPartitionData.get(targetPartition);
        for(Map.Entry<Integer,Long> entry:topicPartitionData.entrySet()){
            if(entry.getValue()<=restOffset){
                restOffset=entry.getValue();
                targetPartition=entry.getKey();
            }
        }
        ProducerRecord newProducerRecord=new ProducerRecord(
                producerRecord.topic(),
                targetPartition,
                producerRecord.timestamp(),
                producerRecord.key(),
                producerRecord.value()
        );
        System.out.println("每一次提交耗时"+(System.currentTimeMillis()-start));
        return newProducerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {


    }



    //获取对应的topic 每一个队列中未消费的数据数量
    public Map<Integer,Long> montiorTopicMessage(String topic){
        KafkaMonitorService kafkaMonitorService =new KafkaMonitorService();
        try(KafkaConsumer<String, String> consumer = KafkaInfoClient.createMonitor();){
            List<TopicPartition> tps = Optional.ofNullable(consumer.partitionsFor(topic))
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(info -> new TopicPartition(info.topic(), info.partition()))
                    .collect(Collectors.toList());
            Map<TopicPartition, Long> endOffsets = consumer.endOffsets(tps);
            Map<Integer, Long> reMap = new HashMap<>();
            //尝试打印 每一个tp 没有消费的消息数量
            for (TopicPartition tp : tps) {
                Long start = 0L;
                if (consumer.committed(tp) != null) {
                    start = consumer.committed(tp).offset();//获取即将
                }
                Long end = endOffsets.get(tp);
                reMap.put(tp.partition(), end - start);
            }
            return reMap;
        }
    }
}
