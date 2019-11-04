package com.kxj.springcloud.controller;

import com.kxj.springcloud.entity.User;
import com.kxj.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/11/4 16:43
 * @Version 1.0
 **/

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @RequestMapping(value = "/user/get/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }
}
