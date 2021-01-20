/**
 * Date: 2021-01-11 10:56
 * Author: xupp
 */

package com.xupp.feginpressuretest;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeginCall {
//    @Bean("build")
//    public Feign.Builder builder(){
//        return
//    }

    @Bean("fegin")
    public DataCenterApi fegin(){
        return RiskApi.getDataCenterApi("http://localhost:8088");
    }
}
