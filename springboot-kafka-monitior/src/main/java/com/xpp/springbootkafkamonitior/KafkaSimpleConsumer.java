/**
 * Date: 2020-09-07 16:43
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import jdk.internal.org.objectweb.asm.tree.IincInsnNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerConfigUtils;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class KafkaSimpleConsumer implements Runnable {

    @Autowired
    KafkaConsumerConfig kafkaConsumerConfig;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    RedisForLua redisForLua;
    @Autowired
    RedisTemplate redisTemplate;
    Random random =new Random();

    @KafkaListener(id = "consumer",topics = "consumer",containerFactory ="consumer_bean" )
    public void consumer(ConsumerRecord<Integer,String> consumerRecord,
                         Acknowledgment acknowledgment) throws InterruptedException {
        try {
            acknowledgment.acknowledge();
            Assert.notNull(consumerRecord.value(),"传过来的空的数据");
//            System.out.println("消费:"+consumerRecord.value());
            TimeUnit.MILLISECONDS.sleep(random.nextInt(200)+100);
        }finally {
            //担心这个地方会超时
            redisForLua.listCasPush("partitions",consumerRecord.partition());
        }
    }



    @PostConstruct
    public void listenRedis(){
        Thread thread =new Thread(this);
        thread.start();
    }


    @Bean("consumer_bean")
    public ConcurrentKafkaListenerContainerFactory consumerListenerContainer() {
        ConcurrentKafkaListenerContainerFactory containerFactory =
                kafkaConsumerConfig.consumerContainerFactory();
        containerFactory.setConcurrency(5);
        return containerFactory;
    }


    @Override
    public void run() {
        while(true){
            Long start =System.currentTimeMillis();
            //阻塞在这里 然后进行发送
            try {
                String message =  KafKaSimpleProducer.queue.poll(10,TimeUnit.SECONDS);
                if(StringUtils.isEmpty(message)){
                    continue;
                }
                int partition = redisForLua.blpop("partitions");
                kafkaTemplate.send("consumer", partition ,message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //执行
//    @Scheduled(fixedDelay = 1)
//    public void pull(){
//        int partition = redisForLua.blpop("partitions");
//        kafkaTemplate.send("consumer", partition ,KafKaSimpleProducer.queue.poll());
//    }


    //如果丢数据



    @Scheduled(cron ="0/1 * * * * ?")
    public void pull(){
        List list= redisTemplate.opsForList().range("partitions",0,-1);
        StringBuffer sb =new StringBuffer();
        for (Object o : list) {
            sb.append(o+",");
        }
        System.out.println(sb.toString());
    }


    //机器监控
    Map<Integer,Integer> map = new ConcurrentHashMap<>();
    @Scheduled(cron ="0/1 * * * * ?")
    public void montiot(){
        if(!RebalanceMonitor.mark){
            return;
        }
        List list= redisTemplate.opsForList().range("partitions",0,-1);
        for (Integer integer : RebalanceMonitor.s) {
            if(!list.contains(integer)){
                map.putIfAbsent(integer,map.getOrDefault(integer,1));
            }else{
                map.remove(integer);
            }
        }
        Iterator<Integer> iterator = map.keySet().iterator();
        while(iterator.hasNext()) {
            Integer  key = iterator.next();
            Integer value = map.get(key);
            if(value==120){
                //重新放回丢列
                redisForLua.listCasPush("partitions",key);
                map.remove(key);
            }

        }
    }
}