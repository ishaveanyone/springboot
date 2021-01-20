/**
 * Date: 2020-11-19 16:53
 * Author: xupp
 */

package com.xupp.springbootkafka;

import ch.qos.logback.core.util.TimeUtil;
import com.xupp.springbootkafka.config.KafkaConsumerConfig;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class KafkaConsumer {


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

    @KafkaListener(id = RiskKafkaConstants.MARKET_PRE_SELECT_TOPIC, topics = RiskKafkaConstants.MARKET_PRE_SELECT_TOPIC, containerFactory = RiskKafkaConstants.MARKET_PRE_SELECT_BEAN)
    public void kafkaListener(ConsumerRecord record, Acknowledgment ack) {
//        ConcurrentKafkaListenerContainerFactory containerFactory =
//                context.getBean(RiskKafkaConstants.MARKET_PRE_SELECT_TOPIC+RiskKafkaConstants.MARKET_PRE_SELECT_BEAN,ConcurrentKafkaListenerContainerFactory.class);
        vector.add(RiskKafkaConstants.MARKET_PRE_SELECT_TOPIC + Thread.currentThread().getName() + "---------------" + record.value());
        ack.acknowledge();
        try {
            TimeUnit.SECONDS.sleep(1);
            vector.remove(RiskKafkaConstants.MARKET_PRE_SELECT_TOPIC + Thread.currentThread().getName() + "---------------" + record.value());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Bean(RiskKafkaConstants.MARKET_PRE_SELECT_BEAN)
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory containerFactory = kafkaConsumerConfig.getContainerFactory();
        containerFactory.setConcurrency(RiskKafkaConstants.MARKET_PRE_SELECT_NUM);
//        thread.start();
        return containerFactory;

    }


}
