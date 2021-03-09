/**
 * Date: 2021-02-20 18:23
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior.old;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.*;

public class ProducerMontiorRedisInterceptor implements ProducerInterceptor {


    String quene="kafka-partitioion-quene";


    //定义一段代码实现 zset 的pop操作   使用lua脚本实现
    private static final String ZSET_LPOP_SCRIPT = "local result = redis.call('ZRANGE', KEYS[1], 0, 0)\n" + "local element = result[1]\n" + "if element then\n" + " redis.call('ZREM', KEYS[1], element)\n" + " return element\n" + "else\n" + " return nil\n" + "end";

    //如果发送的消息超过了线程数 会分批次进行onsend 比如 如果100个消息 10个线程 那么会分10个批次 进行执行onsend 然后执行 onAcknowledgement
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        RedisTemplate redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);
        //开启事务
        Integer  p =  (Integer) redisTemplate.execute(new DefaultRedisScript<>(ZSET_LPOP_SCRIPT, Integer.class), Collections.singletonList(quene));
        if(Objects.isNull(p)){
            return producerRecord;
        }else{
            ProducerRecord newProducerRecord = new ProducerRecord(
                    producerRecord.topic(), p, producerRecord.timestamp(),
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
