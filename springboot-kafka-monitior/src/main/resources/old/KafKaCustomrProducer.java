/**
 * Date: 2020-09-07 16:42
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior.old;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
//@Service
//@RestController
public class KafKaCustomrProducer {

    @Autowired
    KafkaTemplate kafkaTemplate;
    AtomicInteger integer =new AtomicInteger();
    @GetMapping("/send")
    public void sendMessage(){
        kafkaTemplate.send("monitor",String.valueOf(integer.incrementAndGet()));
    }

}