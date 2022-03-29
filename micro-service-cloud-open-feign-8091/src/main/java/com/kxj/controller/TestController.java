package com.kxj.controller;

import com.kxj.feign.EurekaClient8090;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2022/3/30 01:04
 * @desc
 */
@RestController
public class TestController {

    @Autowired
    private EurekaClient8090 eurekaClient8090;

    @GetMapping
    public void test() {
        eurekaClient8090.hello("123");
    }
}
