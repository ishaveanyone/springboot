package com.xupp.springbootcloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;

@SpringBootApplication

public class SpringbootCloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCloudConsumerApplication.class, args);
    }

}
