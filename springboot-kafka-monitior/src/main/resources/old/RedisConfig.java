/**
 * Date: 2020-10-22 18:26
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior.old;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int dataBase;


//    @Bean("redissonClient")
//    public RedissonClient getRedisson(){
//        Config config = new Config();
//        SingleServerConfig singleServerConfig =
//                config.useSingleServer();
//        singleServerConfig.setAddress("redis://" + host + ":" + port);
//        singleServerConfig.setDatabase(dataBase);
//        return Redisson.create(config);
//    }

    @Bean("redisTemplate")
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate();
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        return redisTemplate;
    }

//    @Bean("myRedisTemplate")
//    public RedisTemplate myRedisTemplate(RedisConnectionFactory factory){
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
}