/**
 * Date: 2021-01-11 11:06
 * Author: xupp
 */

package com.xupp.feginpressuretest;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Aspect
@Component
@Slf4j
public class RestAop {

    /**
     * // 控制是否开启日志
     */
    public static Boolean onOff = false;
    Set set =new CopyOnWriteArraySet();
    @Pointcut("execution(public * com.xupp.feginpressuretest.TestRestCallController.*(..))")
    public void pointCutRestDef() {
    }

    @Around("pointCutRestDef()")
    public Object processRst(ProceedingJoinPoint point) throws Throwable {
        Object returnValue = null;
        Long startTime = System.currentTimeMillis();
        set.add(Thread.currentThread());
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