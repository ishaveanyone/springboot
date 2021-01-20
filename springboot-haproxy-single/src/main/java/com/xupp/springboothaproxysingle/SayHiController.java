/**
 * Date: 2021-01-07 14:01
 * Author: xupp
 */

package com.xupp.springboothaproxysingle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class SayHiController {


    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/hi")
    public String sayHi(String mutiPort, HttpServletRequest request ){
        String uuid= UUID.randomUUID().toString();
        Cookie[] hcookies = request.getCookies();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList<>();
        for (Cookie hcookie : hcookies) {
            cookies.add(hcookie.getName()+"="+hcookie.getValue());
        }
        headers.put(HttpHeaders.COOKIE, cookies );
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:80/hi?uuid="+uuid+"&flag=1",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);
       /* ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:8080/hi?uuid="+uuid+"&flag=1",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);*/
        log.info("haproxy-{}--singlegetfrom--:{}---singlesendto{}",uuid,mutiPort,response.getBody());
        return "success";
    }
}
