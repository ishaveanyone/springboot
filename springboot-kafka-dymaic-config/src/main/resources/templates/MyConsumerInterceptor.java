/**
 * Date: 2020-10-20 16:09
 * Author: xupp
 */

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Time;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyConsumerInterceptor implements ConsumerInterceptor<String, String> {


    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> consumerRecords) {
        Map<TopicPartition, List<ConsumerRecord<String, String>>> newRecords
                = new HashMap<>();
        ConsumerRecord consumerRecord =
                new ConsumerRecord("xpp_test", 0, 0, null, 1000);
        for (TopicPartition tp : consumerRecords.partitions()) {
            List<ConsumerRecord<String, String>> tpRecords =
                    consumerRecords.records(tp);
            List<ConsumerRecord<String, String>> newTpRecords = new ArrayList<>();
            newTpRecords.add(consumerRecord);
            newTpRecords.addAll(tpRecords);
            if (!newTpRecords.isEmpty()) {
                newRecords.put(tp, newTpRecords);
            }
        }
        return new ConsumerRecords<>(newRecords);
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        offsets.forEach((k, v) -> {
            System.out.println(k + "---" + v);
        });
    }

    private String getJsonTrackingMessage(ConsumerRecord<String, String> record) {
        return record.value();
    }

    //拦截器关闭做一些操作
    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {
    }

}
