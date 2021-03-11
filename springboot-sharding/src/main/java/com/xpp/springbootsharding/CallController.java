/**
 * Date: 2021-03-11 10:51
 * Author: xupp
 */

package com.xpp.springbootsharding;

import com.xpp.springbootsharding.mapper.local.TestMapper;
import com.xpp.springbootsharding.mapper.sharding.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@Slf4j
public class CallController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    TestMapper testMapper;
    @GetMapping("/call")
    public String call(){
        User user =new User();
        user.setCreateTime(new Date());
        user.setName(UUID.randomUUID().toString());
        userMapper.save(user);
        return user.getId().toString();
    }


    @Scheduled(cron = "0/1 * * * * ?")
    public void insetTest(){
        Test test =new Test();
        test.setName(UUID.randomUUID().toString());
        testMapper.insert(test);
    }


    @PostConstruct
    public void createTable(){
        String oldSuffix = getYYYYMM(new Date());
        String newSuffix = getYYYYMM(add5Days(new Date()));
        String sql = "create table if not exists `test`.user_" + newSuffix + " like `test`.user_" + oldSuffix;
        try{
            userMapper.createTable(sql);
        }catch (Exception e){
            log.error("定时任务创建t_app_info_new新表失败",e);
        }
    }

    public static String getYYYYMM(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        return simpleDateFormat.format(date);
    }
    public static Date add5Days(Date date){
        Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(date);
        nowTime.add(Calendar.DATE, 30);
        //测试  加一个小时
        return nowTime.getTime();
    }
}
