package com.kxj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author kxj
 * @date 2020/9/23 0:02
 * @desc
 */
@SpringBootApplication
@EnableEurekaClient  // eureka服务端
@EnableDiscoveryClient  // 向服务注册中心注册
public class EurekaProviderApplication8083 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaProviderApplication8083.class, args);
    }
}
