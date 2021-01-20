/**
 * Date: 2021-01-11 10:52
 * Author: xupp
 */

package com.xupp.feginpressuretestprovider;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/delay1/{time}")
    public Map delayGet1(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay2/{time}")
    public Map delayGet2(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay3/{time}")
    public Map delayGet3(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay4/{time}")
    public Map delayGet4(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay5/{time}")
    public Map delayGet5(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay6/{time}")
    public Map delayGet6(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay7/{time}")
    public Map delayGet7(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay8/{time}")
    public Map delayGet8(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay9/{time}")
    public Map delayGet9(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay10/{time}")
    public Map delayGet10(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay11/{time}")
    public Map delayGet11(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }
    @GetMapping("/delay12/{time}")
    public Map delayGet12(@PathVariable("time") String time) throws InterruptedException {
        Map map =new HashMap();
        map.put("result","success");
        return map;
    }


}
