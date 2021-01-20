/**
 * Date: 2020-11-19 16:58
 * Author: xupp
 */

package com.xupp.springbootkafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.AnnotatedType;
import java.util.Random;
import java.util.UUID;

@RequestMapping("/test")
@RestController
public class SendController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("/send")
    public String send() {
        kafkaTemplate.send(RiskKafkaConstants.MARKET_PRE_SELECT_TOPIC, UUID.randomUUID().toString());
        return "success";
    }

    @GetMapping("/send2")
    public String send2() {
        kafkaTemplate.send(RiskKafkaConstants.PRE_RISK_TOPIC, UUID.randomUUID().toString());
        return "success";
    }
}
