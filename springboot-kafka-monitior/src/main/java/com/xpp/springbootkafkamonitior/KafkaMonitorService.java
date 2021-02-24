package com.xpp.springbootkafkamonitior;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.jar.JarEntry;

//@Data
//@Service
//@Slf4j
public class KafkaMonitorService {
//    @Value("${kafka.group-id}")
//    private String[] groupId;
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;


    private Map<String, Map<String, Long>> topicSendOffset = new HashMap<>();

    private Map<String, Map<String, Long>> topicConsumerOffset = new HashMap<>();

    private static AdminClient client;

    private static Map<String, KafkaConsumer<String, String>> kafkaConsumerMap = new HashMap<>();

    static {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        client = AdminClient.create(props);
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,  "127.0.0.1:9092");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 禁止自动提交位移
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumers");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumerMap.put("test-consumers", new KafkaConsumer<>(properties));
    }

//    @Scheduled(cron = "30 0/5 * * * *")
//    public void doMonitor() {
//        log.info("Start kafka monitor!");
//        Long start = System.currentTimeMillis();
//        List<OffsetInfo> offsetInfos = new ArrayList<>();
//        Arrays.stream(groupId).forEach(groupID -> {
//            OffsetInfo offsetInfo = lagOf(groupID);
//            if (offsetInfo != null) {
//                offsetInfos.add(offsetInfo);
//                Map<String, Long> newConsumerOffset = offsetInfo.getConsumerOffset();
//                Map<String, Long> newSendOffset = offsetInfo.getSendOffset();
//                Map<String, Long> oldConsumerOffset = getValue(topicConsumerOffset, groupID);
//                Map<String, Long> oldSendOffset = getValue(topicSendOffset, groupID);
//                newConsumerOffset.forEach((key, value) -> {
//                    Long sendOffset = newSendOffset.get(key);
//                    // 计算(Lag)滞后值
//                    Long lag = sendOffset - value;
//                    // 如果滞后值大于等于5，且最近两次的消费偏移值一样，则表示消费出现问题
//                    if (lag >= 5L && value.equals(oldConsumerOffset.get(key))) {
//
//                    }
//                    oldConsumerOffset.put(key, value);
//                    oldSendOffset.put(key, sendOffset);
//                });
//                topicSendOffset.put(groupID, oldSendOffset);
//                topicConsumerOffset.put(groupID, oldConsumerOffset);
//            }
//        });
////        log.info("offsetInfo:" + JSON.toJSONString(offsetInfos));
//        log.info("End kafka monitor->" + (System.currentTimeMillis() - start) + "ms");
//    }

    /**
     * 获取topic偏移量
     *
     * @param groupID
     * @return
     */
    public OffsetInfo lagOf(String groupID) {

        Long start =System.currentTimeMillis();
        OffsetInfo offsetInfo = new OffsetInfo();

        ListConsumerGroupOffsetsResult result = client.listConsumerGroupOffsets(groupID);

        try {
            Map<TopicPartition, OffsetAndMetadata> consumedOffsets = result.
                    partitionsToOffsetAndMetadata().
                        get(10, TimeUnit.SECONDS);
            consumedOffsets.forEach((key, value) -> offsetInfo.getConsumerOffset().put(key.topic()+"-"+key.partition(), value.offset()));
            final KafkaConsumer<String, String> consumer = kafkaConsumerMap.get(groupID);
            Map<TopicPartition, Long> endOffsets = consumer.endOffsets(consumedOffsets.keySet());
            endOffsets.forEach((key, value) -> offsetInfo.getSendOffset().put(key.topic()+"-"+key.partition(), value));

//            consumedOffsets.forEach((key, value) -> offsetInfo.getConsumerOffset().put(key.topic(), value.offset()));
//            final KafkaConsumer<String, String> consumer = kafkaConsumerMap.get(groupID);
//            Map<TopicPartition, Long> endOffsets = consumer.endOffsets(consumedOffsets.keySet());
//            endOffsets.forEach((key, value) -> offsetInfo.getSendOffset().put(key.topic(), value));
            offsetInfo.setGroupId(groupID);
            System.out.println("执行一次花费时间"+(System.currentTimeMillis()-start));
            return offsetInfo;
        } catch (InterruptedException e) {
//            log.error("", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
//            log.error("", e);
        } catch (TimeoutException e) {
//            log.error("Timed out when getting lag for consumer group ", e);
        }
        return null;

    }

    private Map<String, Long> getValue(Map<String, Map<String, Long>> map, String key) {
        Map<String, Long> longMap = map.get(key);
        return longMap == null ? new HashMap<>() : longMap;
    }

    public static void main(String[] args) {
        KafkaMonitorService kafkaMonitorService =new KafkaMonitorService();
        kafkaMonitorService.lagOf("test-consumers");
    }

    @Data
    public static class OffsetInfo {
        private Map<String, Long> sendOffset = new HashMap<>();

        private Map<String, Long> consumerOffset = new HashMap<>();

        private String groupId;
    }

}
