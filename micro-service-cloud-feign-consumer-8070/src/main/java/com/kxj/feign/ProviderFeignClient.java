package com.kxj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiangjin.kong
 * @date 2020/9/24 16:47
 */

/**
 * 指定服务名 @FeignClient（"服务名"）
 */
@FeignClient("eureka-provider")
public interface ProviderFeignClient {

    /**
     * 调用服务提供者
     * @return
     */
    @RequestMapping("/")
    String consumer();
}
