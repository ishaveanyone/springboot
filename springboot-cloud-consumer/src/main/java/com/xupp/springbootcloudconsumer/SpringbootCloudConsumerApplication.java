package com.xupp.springbootcloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableCircuitBreaker
@SpringBootApplication(exclude = MongoDataAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients (basePackages = {"com.xupp.springbootcloudapi"})
public class SpringbootCloudConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootCloudConsumerApplication.class, args);
    }
}
