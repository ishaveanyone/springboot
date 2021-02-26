/**
 * Date: 2021-02-20 18:23
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ProducerMontiorRedisInterceptor implements ProducerInterceptor {


    String quene="kafka-partitioion-quene";

    //如果发送的消息超过了线程数 会分批次进行onsend 比如 如果100个消息 10个线程 那么会分10个批次 进行执行onsend 然后执行 onAcknowledgement
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        RedisTemplate redisTemplate = (RedisTemplate) SpringContextUtil.getBean("myRedisTemplate", RedisTemplate.class);
        Set<ZSetOperations.TypedTuple<Integer>> p = redisTemplate.opsForZSet().reverseRangeWithScores(quene,0,-1);


        if(Objects.isNull(p)||p.isEmpty()){
            return producerRecord;
        }else{
            ProducerRecord newProducerRecord = new ProducerRecord(
                    producerRecord.topic(), p.iterator().next().getValue(), producerRecord.timestamp(),
                    producerRecord.key(), producerRecord.value()
            );
            return newProducerRecord;
        }
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



}
