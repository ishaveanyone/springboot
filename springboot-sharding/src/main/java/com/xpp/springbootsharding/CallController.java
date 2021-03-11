/**
 * Date: 2021-03-11 10:51
 * Author: xupp
 */

package com.xpp.springbootsharding;

import com.xpp.springbootsharding.mapper.sharding.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController

public class CallController {
    @Autowired
    UserMapper userMapper;
    @GetMapping("/call")
    public String call(){
        User user =new User();
        user.setCreateTime(new Date());
        user.setName(UUID.randomUUID().toString());
        userMapper.save(user);
        return user.getId().toString();
    }
}
