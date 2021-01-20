/**
 * Date: 2021-01-11 11:06
 * Author: xupp
 */

package com.xupp.feginpressuretestprovider;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class RestAop {

    /**
     * // 控制是否开启日志
     */
    public static Boolean onOff = false;
    Set set =new CopyOnWriteArraySet();
    @Pointcut("execution(public * com.xupp.feginpressuretestprovider.TestController.*(..))")
    public void pointCutRestDef() {
    }

    @Around("pointCutRestDef()")
    public Object processRst(ProceedingJoinPoint point) throws Throwable {
        Object returnValue = null;
        Long startTime = System.currentTimeMillis();
        set.add(Thread.currentThread());
        log.info("新的 thread 到达---{}",set.size());
        if(set.size()<10){
            TimeUnit.SECONDS.sleep(10);
        }
        try {
            returnValue = point.proceed(point.getArgs());
        } catch (Exception e) {
            // 请求异常处理
            throw e;
        }
        Long endTime = System.currentTimeMillis();
        log.info("请求时间:------>{}-----{}",(endTime-startTime)/1000,set.size());
        set.remove(Thread.currentThread());
        return returnValue;
    }

}