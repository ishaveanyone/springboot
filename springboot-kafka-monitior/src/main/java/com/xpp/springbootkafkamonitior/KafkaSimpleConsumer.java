/**
 * Date: 2020-09-07 16:43
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import com.lmax.disruptor.dsl.Disruptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
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




    @KafkaListener(id="monitor", topics = "monitor",containerFactory ="monitor_bean" )
    public void consumer1_1(List<String> messages, Acknowledgment acknowledgment) throws InterruptedException {
        Long start=System.currentTimeMillis();
        acknowledgment.acknowledge();
        System.out.println(messages.size());
        System.out.println("请求完成");
    }

    //发送 可以做到 每一秒 每一秒获取kafka的状态么 然后本次send 的时候 对比状态
    //消费 --- 还是一条一条读取 查看当前实例中的线程池 转台 是否存在可用线程 如果有 就消费 ，新加一个topic 用于发送阻塞的消息
    //如果当前节点的队列全部阻塞 发送到这个topic 拿到这个消息之后查看当前节点存在可用线程 如果有 就消费
    //a --- b ---- c ---- d
    //--

    @Bean("monitor_bean")
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory containerFactory = kafkaConsumerConfig.getContainerFactory();
        containerFactory.setConcurrency(10);
        containerFactory.setBatchListener(true);
        //维持
        return containerFactory;
    }

}