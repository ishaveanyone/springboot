/**
 * Date: 2021-01-07 14:01
 * Author: xupp
 */

package com.xupp.springboothaproxymuti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class SayHiController {
    @Value("${server.port}")
    private Integer port;

    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/hi")
    public String sayHi(String uuid,String flag){
        if("1".equals(flag)){
            log.info("haproxy-{}muti-send:{}",uuid,port);
            return ""+port;
        }
        log.info("haproxy->{}muti->send:{}",uuid,port);
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList<>();
        if(port.equals(8088)){
            cookies.add("SESSION_COOKIE=server1");
            headers.put(HttpHeaders.COOKIE, cookies );
        }else {
            cookies.add("SESSION_COOKIE=server2");
            headers.put(HttpHeaders.COOKIE,cookies );
        }
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8089/hi?mutiPort="+port,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);
        /*ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:8089/hi?mutiPort="+port,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);*/
        return ""+port;
    }




}
