package com.xupp.feginpressuretest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/test")
public interface DataCenterApi {


    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay1")
    @RequestLine("GET " + "/test/delay1/{time}")
    Map testDelay1(@PathVariable("time")@Param("time") String time);


    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay2")
    @RequestLine("GET " + "/test/delay2/{time}")
    Map testDelay2(@PathVariable("time")@Param("time") String time);

    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay3")
    @RequestLine("GET " + "/test/delay3/{time}")
    Map testDelay3(@PathVariable("time")@Param("time") String time);

    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay4")
    @RequestLine("GET " + "/test/delay4/{time}")
    Map testDelay4(@PathVariable("time")@Param("time") String time);

    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay5")
    @RequestLine("GET " + "/test/delay5/{time}")
    Map testDelay5(@PathVariable("time")@Param("time") String time);
    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay6")
    @RequestLine("GET " + "/test/delay6/{time}")
    Map testDelay6(@PathVariable("time")@Param("time") String time);
    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay7")
    @RequestLine("GET " + "/test/delay7/{time}")
    Map testDelay7(@PathVariable("time")@Param("time") String time);
    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay8")
    @RequestLine("GET " + "/test/delay8/{time}")
    Map testDelay8(@PathVariable("time")@Param("time") String time);
    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay9")
    @RequestLine("GET " + "/test/delay9/{time}")
    Map testDelay9(@PathVariable("time")@Param("time") String time);
    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay10")
    @RequestLine("GET " + "/test/delay10/{time}")
    Map testDelay10(@PathVariable("time")@Param("time") String time);
    @Headers("Content-Type: application/json")
    @GetMapping(path ="delay11")
    @RequestLine("GET " + "/test/delay11/{time}")
    Map testDelay11(@PathVariable("time")@Param("time") String time);
}
