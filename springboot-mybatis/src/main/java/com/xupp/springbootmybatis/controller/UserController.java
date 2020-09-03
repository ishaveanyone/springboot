/**
 * Date: 2020-09-02 10:17
 * Author: xupp
 */

package com.xupp.springbootmybatis.controller;

import com.xupp.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    //主节点进行更新
    @PostMapping("/user")
    public Object save(String name){
       return userService.save(name);
    }

    //从节点进行获取
    @GetMapping("/all")
    public Object all(){
        return userService.getAll();
    }
}
