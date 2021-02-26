/**
 * Date: 2021-02-22 10:48
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.MetadataResponse;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用于获取kafka 指定topic partion对应的数据情况
 */

public class KafkaInfoClient{
//
//    static Map<Long,KafkaConsumer>  consumerMap = new ConcurrentHashMap<>();





    public static KafkaConsumer createMonitor(){
//       Long threadId = Thread.currentThread().getId();
////       if(!consumerMap.containsKey(threadId)){
////           consumerMap.put(threadId,new KafkaConsumer<>(new Properties(){{
////               put("bootstrap.servers", "127.0.0.1:9092");
////               put("group.id", "test-consumers");
////               put("enable.auto.commit", "false");
////               put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
////               put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
////           }}));
////       }
////       return consumerMap.getOrDefault(threadId,new KafkaConsumer<>(new Properties(){{
////           put("bootstrap.servers", "127.0.0.1:9092");
////           put("group.id", "test-consumers");
////           put("enable.auto.commit", "false");
////           put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
////           put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
////       }}));
        return new KafkaConsumer<>(new Properties(){{
           put("bootstrap.servers", "127.0.0.1:9092");
           put("group.id", "test-consumers");
           put("enable.auto.commit", "false");
           put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
           put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
       }});
    }



    /*public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = createMonitor();
        while (true) {
            try {
                List<TopicPartition> tps = Optional.ofNullable(consumer.partitionsFor("monitor"))
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(info -> new TopicPartition(info.topic(), info.partition()))
                        .collect(Collectors.toList());
                Map<TopicPartition, Long> endOffsets = consumer.endOffsets(tps);
                Map<TopicPartition, Long> beginOffsets = consumer.beginningOffsets(tps);
                //尝试打印 每一个tp 没有消费的消息数量
                for (TopicPartition tp : tps) {
                    Long start = 0L;//获取即将
                    if(consumer.committed(tp)!=null){
                        start = consumer.committed(tp).offset();
                    }
                    Long begin = beginOffsets.get(tp);//获取即将
                    Long end = endOffsets.get(tp);
                    StringBuffer sb=new StringBuffer();
//                    sb.append(tp.partition() + "待消费的消息数量:" + (end - start)+"     ");
                    sb.append(tp.partition() + "已经收取到的总消息数量 ： " + (end - begin));
                    System.out.println(sb.toString());
                }
                System.out.println("-------------------------------------------------------------");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    //实时获取redis中的数据情况
    public static void main(String[] args) {
        Jedis jedis = new Jedis("47.102.100.218",8092);
        // 如果 Redis 服务设置来密码，需要下面这行，没有就不需要
        jedis.auth("xiaopeng");
        jedis.select(13);
        List list = jedis.lrange("kafka-quene_pre_risk_topic",0,-1);
        for (Object s : list) {
            System.out.println(s);
        }

    }


}