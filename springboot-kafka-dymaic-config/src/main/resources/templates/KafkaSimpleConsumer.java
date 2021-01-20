/**
 * Date: 2020-09-07 16:43
 * Author: xupp
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaSimpleConsumer {

    @Autowired
    KafkaConsumerConfig kafkaConsumerConfig;

    @KafkaListener(id = "xpp_test_bean", topics = "xpp_test", containerFactory = "listenerContainer")
    public void consumer1_1(ConsumerRecord record, Acknowledgment ack) {

        int i = Integer.valueOf(record.value().toString());
        System.out.println(i);
        ack.acknowledge();
    }


    @KafkaListener(id = "xpp_test_bean_2", topics = "topic.manual.create", containerFactory = "listenerContainer")
    public void consumer1_2(ConsumerRecord record, Acknowledgment ack) {
        if ("lalalalal".equals(record.topic())) {
            System.out.println(record.topic());
        }
        ack.acknowledge();
    }

    @Bean("listenerContainer")
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory containerFactory = kafkaConsumerConfig.getConcurrentContainerFactory();
        containerFactory.setConcurrency(3);
        return containerFactory;
    }

    public static void main(String[] args) {

    }
}