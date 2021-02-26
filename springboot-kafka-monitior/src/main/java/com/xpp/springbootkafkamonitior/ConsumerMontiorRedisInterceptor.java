/**
 * Date: 2021-02-20 18:23
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Objects;

public class ConsumerMontiorRedisInterceptor implements ConsumerInterceptor {


    @Override
    public ConsumerRecords onConsume(ConsumerRecords records) {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map offsets) {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
