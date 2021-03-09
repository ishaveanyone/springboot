/**
 * Date: 2020-09-07 16:43
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior.old;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
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

//    @KafkaListener(id = "monitor",topics = "monitor",containerFactory ="monitor_bean" )
//    public void consumer1_1(ConsumerRecord<Integer,String> consumerRecord, Acknowledgment acknowledgment) throws InterruptedException {
//        acknowledgment.acknowledge();
//        Long start = System.currentTimeMillis();
//        Integer message = Integer.valueOf(consumerRecord.value());
//        TimeUnit.SECONDS.sleep(message);
//        redisTemplate.opsForZSet().add(quene,consumerRecord.partition(),(System.currentTimeMillis()-start));
//    }

    //如果使用自己的thread 那么久定义一组thread 在发版过程中 使用 这组线程完成消费 在发版借宿

    @KafkaListener(id = "monitor",topics = "monitor",containerFactory ="monitor_bean" )
    public void consumer1_2(ConsumerRecord<Integer,String> consumerRecord, Acknowledgment acknowledgment) throws InterruptedException {

        acknowledgment.acknowledge();

        Long start = System.currentTimeMillis();
        Integer message = Integer.valueOf(consumerRecord.value());
        TimeUnit.SECONDS.sleep(message);
        redisTemplate.opsForZSet().add(quene,consumerRecord.partition(),(System.currentTimeMillis()-start));
        // 发送
        new ArrayBlockingQueue<>().add();
        kafka.send(topic,partion,"");
    }

    @KafkaListener(id = "monitor-thread",topics = "monitor",containerFactory ="monitor_bean" )
    public void consumer1_3(ConsumerRecord<Integer,String> consumerRecord, Acknowledgment acknowledgment) throws InterruptedException {
        //发送不只是partion
        new ArrayBlockingQueue<>().remove();
        acknowledgment.acknowledge();

        Long start = System.currentTimeMillis();
        Integer message = Integer.valueOf(consumerRecord.value());
        TimeUnit.SECONDS.sleep(message+"_"+);
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
        Set<ZSetOperations.TypedTuple<Integer>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores("kafka-quene_pre_risk_topic",0,-1);
        for (ZSetOperations.TypedTuple<Integer> integerTypedTuple : set) {
            System.out.println(integerTypedTuple.getValue()+":"+integerTypedTuple.getScore());
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