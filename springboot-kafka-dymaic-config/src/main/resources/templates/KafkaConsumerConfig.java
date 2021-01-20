import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrap;
    @Value("${spring.kafka.consumer.group-id}")
    String groupId;

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        List<String> interceptors = new ArrayList<>();
        interceptors.add("com.xupp.springbootkafka.MyConsumerInterceptor");
//        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptors);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        return props;
    }

    public ConcurrentKafkaListenerContainerFactory getConcurrentContainerFactory() {

        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
        //AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE)
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
////
//    @Bean //创建一个kafka管理类，相当于rabbitMQ的管理类rabbitAdmin,没有此bean无法自定义的使用adminClient创建topic
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> props = new HashMap<>();
//        //配置Kafka实例的连接地址
//        //kafka的地址，不是zookeeper
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
//        KafkaAdmin admin = new KafkaAdmin(props);
//        return admin;
//    }
////
//    @Bean  //kafka客户端，在spring中创建这个bean之后可以注入并且创建topic,用于集群环境，创建对个副本
//    public AdminClient adminClient() {
//        return AdminClient.create(kafkaAdmin().getConfig());
//    }


}
