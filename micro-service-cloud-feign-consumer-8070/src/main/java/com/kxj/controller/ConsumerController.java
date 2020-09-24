package com.kxj.controller;

import com.kxj.feign.ProviderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangjin.kong
 * @date 2020/9/24 16:49
 */
@RestController
public class ConsumerController {

    @Autowired
    ProviderFeignClient providerFeignClient;

    @GetMapping("/consumer")
    public String consumer() {
        return providerFeignClient.consumer();
    }
}
