/**
 * Date: 2020-09-07 16:43
 * Author: xupp
 */

package com.xupp.springbootkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.internals.Topic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSimpleConsumer {

    @KafkaListener( topics = "xupp")
    public void consumer1_1(String message) {
        System.out.println("消费者收到消息:" + message);
    }
}