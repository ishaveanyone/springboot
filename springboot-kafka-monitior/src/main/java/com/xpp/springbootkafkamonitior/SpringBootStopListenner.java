/**
 * Date: 2021-03-09 12:00
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SpringBootStopListenner implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        System.out.println("SpringBoot将要停止");
        LinkedBlockingQueue<String> queue  =   KafKaSimpleProducer.queue;
        byte[] bytes =  SerializationUtils.serialize(queue);
        try {
            FileOutputStream  out =new FileOutputStream(new File("H:/1.log"));
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}