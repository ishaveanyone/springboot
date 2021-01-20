/**
 * Date: 2020-09-07 16:42
 * Author: xupp
 */

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class KafKaCustomrProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    static AtomicInteger atomicInteger = new AtomicInteger();

    @RequestMapping("/send/xpp_test")
    public String sendMessage() {
        kafkaTemplate.send("xpp_test", String.valueOf(atomicInteger.incrementAndGet()));
        return "success";
    }

    @RequestMapping("/send/xpp_test_2")
    public String sendMessage2() {
        kafkaTemplate.send("xpp_test_2", String.valueOf(atomicInteger.incrementAndGet()));
        return "success";
    }

    @RequestMapping("/send/topicmanualrceate")
    public String sendMessage3() {
        kafkaTemplate.send("topic.manual.create", String.valueOf(atomicInteger.incrementAndGet()));
        return "success";
    }

//    @Autowired
//    private AdminClient adminClient;
//    @PostConstruct
//    public void testCreateTopic() throws InterruptedException {
//        // 这种是手动创建 //10个分区，一个副本
//        // 分区多的好处是能快速的处理并发量，但是也要根据机器的配置
//        NewTopic topic = new NewTopic("topic.manual.create", 10, (short) 1);
//        adminClient.createTopics(Arrays.asList(topic));
//        Thread.sleep(1000);
//    }


}