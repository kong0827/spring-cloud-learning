package com.kxj.springcloud.controller;

import com.kxj.springcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/11/4 17:20
 * @Version 1.0
 **/

@RestController
public class UserController {

//    private static String REST_URL_PREFIX = "http://localhost:7000";
    private static String REST_URL_PREFIX = "http://MICROSERVICECLOUD-CLIENT:7000";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "consumer/user/list")
    public List<User> getAllUser() {
        List forObject = restTemplate.getForObject(REST_URL_PREFIX + "/user/list", List.class);
        return forObject;
    }

    @RequestMapping(value = "consumer/user/get/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/user/get/" + id, User.class);

    }


}
