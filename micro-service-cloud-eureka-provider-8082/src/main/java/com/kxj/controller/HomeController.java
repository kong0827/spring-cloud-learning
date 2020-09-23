package com.kxj.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2020/9/23 23:43
 * @desc
 */
@RestController
public class HomeController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/")
    public String home() {
        return "hello world, port:" + port;
    }
}
