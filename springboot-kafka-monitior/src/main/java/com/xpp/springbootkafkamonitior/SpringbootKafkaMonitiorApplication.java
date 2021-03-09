package com.xpp.springbootkafkamonitior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringbootKafkaMonitiorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootKafkaMonitiorApplication.class, args);
    }

}
