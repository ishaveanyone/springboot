/**
 * Date: 2020-09-07 16:43
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KafkaSimpleConsumer {

    @Autowired
    KafkaConsumerConfig kafkaConsumerConfig;

    @KafkaListener( topics = "monitor",containerFactory ="monitor_bean" )
    public void consumer1_1(ConsumerRecord record, Acknowledgment ack) throws InterruptedException {
        Long start=System.currentTimeMillis();
        Long message = Long.valueOf(record.value().toString());
        //3 6 9 这三个topic 应该处理很慢 那么久少放数据
        ack.acknowledge();
        if(message%3==0){
            System.out.println(Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(10); // 等待10s
        }
//        System.out.println("消费者收到消息:" + record.value());
    }



    @Bean("monitor_bean")
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory containerFactory = kafkaConsumerConfig.getContainerFactory();
        containerFactory.setConcurrency(10);
        return containerFactory;
    }
}