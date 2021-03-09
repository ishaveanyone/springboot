/**
 * Date: 2020-09-07 16:42
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@RestController
public class KafKaSimpleProducer {

    @Value("${server.port}")
    Integer port;

    @Autowired
    KafkaTemplate kafkaTemplate;


    //先使用quene 如果所有的线程全部堵塞 应该就 切换存放message的方案
    public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();


    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(cron = "0/1 * * * * ?")
    public void sendMessage(){
        //如果quene大于一定数据的时候我把这个数据存放到mysql 或者存放到 改变存数据的方案
//        System.out.println("未消费的数据:"+queue.size());
        for(int i=0;i<1000;i++) {
            queue.add(port + "-" + System.currentTimeMillis());
        }
    }




}