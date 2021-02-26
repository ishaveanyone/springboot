/**
 * Date: 2020-09-07 16:42
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@Slf4j
@Service
@RestController
public class KafKaCustomrProducer {

    @Autowired
    KafkaTemplate kafkaTemplate;
    @GetMapping("/send")
    public void sendMessage(){

        //尝试发送
        for(int i=100;i>0;i--){
            kafkaTemplate.send("monitor",i+"");
        }
//        System.out.println("发送成功。。。。。。");
    }

}