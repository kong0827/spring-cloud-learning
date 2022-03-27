package com.kxj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author kxj
 * @date 2022/3/27 22:37
 * @desc
 */
@FeignClient(value = "eureka-client-8090")
public interface EurekaClient8090 {

    @GetMapping("hello")
    String hello(@RequestParam String content);
}
