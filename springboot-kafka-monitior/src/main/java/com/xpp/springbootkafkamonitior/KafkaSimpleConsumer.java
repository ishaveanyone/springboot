/**
 * Date: 2020-09-07 16:43
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import com.lmax.disruptor.dsl.Disruptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerConfigUtils;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KafkaSimpleConsumer {

    @Autowired
    KafkaConsumerConfig kafkaConsumerConfig;

    @Autowired
    RedisTemplate redisTemplate;


//    @Autowired
//    RedissonClient redissonClient;

    String quene="kafka-partitioion-quene";

    @KafkaListener(id = "monitor",topics = "monitor",containerFactory ="monitor_bean" )
    public void consumer1_1(ConsumerRecord<Integer,String> consumerRecord, Acknowledgment acknowledgment) throws InterruptedException {
        acknowledgment.acknowledge();
        Long start = System.currentTimeMillis();
//        int message =  Integer.valueOf(consumerRecord.value());
//        System.out.println(Thread.currentThread().getName());
//        System.out.println("正在消费。。。。。。"+consumerRecord.partition());
        //使用当前的时间戳用来做 打分结果
//        TimeUnit.SECONDS.sleep(3);
//        redisTemplate.opsForList().leftPush(quene,consumerRecord.partition());
        redisTemplate.opsForZSet().add(quene,consumerRecord.partition(),(System.currentTimeMillis()-start));
    }

    // 每秒监控运行状态

//    @Scheduled(cron = "*0/5 * * * * ?")
    public void montior(){
        KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry =
                (KafkaListenerEndpointRegistry) SpringContextUtil.getBean(KafkaListenerConfigUtils.KAFKA_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
                        KafkaListenerEndpointRegistry.class);

        Collection<MessageListenerContainer> containers = kafkaListenerEndpointRegistry.getListenerContainers();
//        for (MessageListenerContainer container : containers) {
//            ConcurrentMessageListenerContainer concurrentMessageListenerContainer =
//                    (ConcurrentMessageListenerContainer)container;
//            concurrentMessageListenerContainer.
//        }
        ConcurrentMessageListenerContainer concurrentMessageListenerContainer
                = (ConcurrentMessageListenerContainer) containers.iterator().next();
        List<KafkaMessageListenerContainer>  kafkaMessageListenerContainers =
                concurrentMessageListenerContainer.getContainers();
        for (KafkaMessageListenerContainer kafkaMessageListenerContainer : kafkaMessageListenerContainers) {
            System.out.println(kafkaMessageListenerContainer.getBeanName()+kafkaMessageListenerContainer.isRunning());
        }

    }

    //监控redis
    @Scheduled(cron = "0/2 * * * * ?")
    public void montior_redis(){
        List<Integer> integers =
                redisTemplate.opsForList().range("kafka-quene_pre_risk_topic",0,-1);
        for (Integer integer : integers) {
            System.out.print(integer+",");
        }
        System.out.println("----------------------------------------------------");
    }


    @Bean("monitor_bean")
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory containerFactory = kafkaConsumerConfig.getContainerFactory();
        containerFactory.setConcurrency(10);
//        containerFactory.setBatchListener(true);
        //维持
        return containerFactory;
    }

}