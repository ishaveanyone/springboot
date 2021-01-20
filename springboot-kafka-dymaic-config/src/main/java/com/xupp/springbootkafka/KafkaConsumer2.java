/**
 * Date: 2020-11-19 16:53
 * Author: xupp
 */

package com.xupp.springbootkafka;

import com.xupp.springbootkafka.config.KafkaConsumerConfig;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Vector;
import java.util.concurrent.TimeUnit;


@Service
public class KafkaConsumer2 {


    @Autowired
    KafkaConsumerConfig kafkaConsumerConfig;

    Vector vector = new Vector();

    @Autowired
    ApplicationContext context;

    Thread thread = new Thread(new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {

                System.out.println(vector.size());
                vector.forEach(System.out::println);
                TimeUnit.SECONDS.sleep(1);
            }
        }
    });

    {
        thread.start();
    }

    @KafkaListener(id = RiskKafkaConstants.PRE_RISK_TOPIC, topics = RiskKafkaConstants.PRE_RISK_TOPIC, containerFactory = RiskKafkaConstants.PRE_RISK_BEAN)
    public void kafkaListener(ConsumerRecord record, Acknowledgment ack) {
//        ConcurrentKafkaListenerContainerFactory containerFactory =
//                context.getBean(RiskKafkaConstants.PRE_RISK_BEAN,ConcurrentKafkaListenerContainerFactory.class);
        vector.add(RiskKafkaConstants.PRE_RISK_BEAN + Thread.currentThread().getName() + "---------------" + record.value());
        ack.acknowledge();
        try {
            TimeUnit.SECONDS.sleep(1);
            vector.remove(RiskKafkaConstants.PRE_RISK_BEAN + Thread.currentThread().getName() + "---------------" + record.value());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Bean(RiskKafkaConstants.PRE_RISK_BEAN)
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory containerFactory = kafkaConsumerConfig.getContainerFactory();
        containerFactory.setConcurrency(RiskKafkaConstants.MARKET_PRE_SELECT_NUM);
//        thread.start();
        return containerFactory;

    }


}
