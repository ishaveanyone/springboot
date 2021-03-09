/**
 * Date: 2021-03-08 14:33
 * Author: xupp
 */

package com.xpp.springbootkafkamonitior;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

//解决redis 无法完成的一些 cas操作 ，check and set
@Service
public class RedisForLua {
    @Autowired
    RedisTemplate redisTemplate;
    String script=
            "if redis.call('EXISTS',KEYS[1]) == 1 then\n" +
            "    redis.call('LREM',KEYS[1],0,ARGV[1])\n" +
            "end\n" +
            "return redis.call('LPUSH',KEYS[1],ARGV[1])\n";
    public Long listCasPush(String key,int value){
        Long result =  (Long) redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(key),value);
        return result;
    }
    public Integer blpop(String key){
        return (Integer) redisTemplate.opsForList().rightPop(key,0, TimeUnit.SECONDS);
    }

}
