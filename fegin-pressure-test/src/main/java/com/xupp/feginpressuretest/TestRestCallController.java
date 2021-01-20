/**
 * Date: 2021-01-11 10:55
 * Author: xupp
 */

package com.xupp.feginpressuretest;

import com.alibaba.fastjson.JSONObject;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestRestCallController {
//    @Autowired
//            @Qualifier("build")
//    Feign.Builder builder;
    @GetMapping("call")
    public String call(){
        log.info("发起请求");
        Feign.Builder  builder = RiskApi.getDataCenterApi();
//        builder.target(DataCenterApi,"http://localhost:8081");
//        builder.target(DataCenterApi,"http://localhost:8081");
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8081").testDelay1(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8082").testDelay2(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
        Executors.newSingleThreadExecutor().execute(()->builder.target(DataCenterApi.class,"http://localhost:8083").testDelay3(System.currentTimeMillis()+""));
//        Executors.newSingleThreadExecutor().execute(()->dataCenterApi.testDelay4(System.currentTimeMillis()+""));
//        Executors.newSingleThreadExecutor().execute(()->dataCenterApi.testDelay5(System.currentTimeMillis()+""));
//        Executors.newSingleThreadExecutor().execute(()->dataCenterApi.testDelay6(System.currentTimeMillis()+""));
         return "ok";
    }

}
