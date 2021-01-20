/**
 * Date: 2020-11-20 13:43
 * Author: xupp
 */

package com.xupp.springbootkafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerConfigUtils;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;


@RequestMapping
@RestController
public class ChangeThreadNumController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/{num}")
    public void change(@PathVariable Integer num) {


        KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry =
                context.getBean(KafkaListenerConfigUtils.KAFKA_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
                        KafkaListenerEndpointRegistry.class);

        Collection<MessageListenerContainer> containers = kafkaListenerEndpointRegistry.getListenerContainers();
        ConcurrentMessageListenerContainer concurrentMessageListenerContainer
                = (ConcurrentMessageListenerContainer) containers.iterator().next();
        concurrentMessageListenerContainer.setConcurrency(num);
        concurrentMessageListenerContainer.stop();
        concurrentMessageListenerContainer.start();
        //

// 获取BeanFactory
        String[] names = context.getBeanNamesForType(KafkaConsumer.class);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();

        defaultListableBeanFactory.removeBeanDefinition(names[0]);
        defaultListableBeanFactory.removeBeanDefinition(RiskKafkaConstants.MARKET_PRE_SELECT_BEAN);
        RiskKafkaConstants.MARKET_PRE_SELECT_NUM = num;
        //创建bean信息.
        BeanDefinitionBuilder beanDefinitionBuilder0 = BeanDefinitionBuilder.genericBeanDefinition(ConcurrentKafkaListenerContainerFactory.class);
        defaultListableBeanFactory.registerBeanDefinition(RiskKafkaConstants.MARKET_PRE_SELECT_BEAN, beanDefinitionBuilder0.getBeanDefinition());
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(KafkaConsumer.class);
        defaultListableBeanFactory.registerBeanDefinition(names[0], beanDefinitionBuilder.getBeanDefinition());
    }


    @GetMapping("/stop")
    public String stop() {
        KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry =
                context.getBean(KafkaListenerConfigUtils.KAFKA_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
                        KafkaListenerEndpointRegistry.class);

        Collection<MessageListenerContainer> containers = kafkaListenerEndpointRegistry.getListenerContainers();
        ConcurrentMessageListenerContainer concurrentMessageListenerContainer
                = (ConcurrentMessageListenerContainer) containers.iterator().next();
        concurrentMessageListenerContainer.stop();
        return "";
    }

    @GetMapping("/start")
    public String start() {
        KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry =
                context.getBean(KafkaListenerConfigUtils.KAFKA_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
                        KafkaListenerEndpointRegistry.class);

        Collection<MessageListenerContainer> containers = kafkaListenerEndpointRegistry.getListenerContainers();
        ConcurrentMessageListenerContainer concurrentMessageListenerContainer
                = (ConcurrentMessageListenerContainer) containers.iterator().next();
        concurrentMessageListenerContainer.start();
        return "";
    }

}
