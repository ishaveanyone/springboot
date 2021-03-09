/**
 * Date: 2021-03-09 12:00
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SpringBootStartListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("SpringBoot启动成功");

        //刷新数据到本地的log中
        try {
            File file = new File("H:/1.log");
            if(!file.exists()){
                return;
            }
            FileInputStream in =new FileInputStream(file);
            int count = in.available();
            byte[] bytes=new byte[count];
//            KafKaSimpleProducer.queue.addAll((LinkedBlockingQueue<String>)SerializationUtils.deserialize(bytes));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}