/**
 * Date: 2021-03-04 11:37
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class RebalanceMonitor implements ConsumerRebalanceListener {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisForLua redisForLua;

    public static Set<Integer> s = new CopyOnWriteArraySet<>();
    public static volatile boolean mark = true;

    //发送到redis
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        mark=false;
        redisTemplate.delete("partitions");
        s.clear();
        for (TopicPartition partition : partitions) {
            log.info("平衡前，停止接收消息之后"+partition.partition());
        }
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        mark=true;
        for (TopicPartition partition : partitions) {
            log.info("平衡后，开始接收消息之前"+partition.topic()+partition.partition());
            if("consumer".equals(partition.topic())){
                redisForLua.listCasPush("partitions",partition.partition());
                s.add(partition.partition());
            }
        }
    }

}
