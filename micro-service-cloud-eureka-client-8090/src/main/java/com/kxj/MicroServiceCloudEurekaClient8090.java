package com.kxj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xiangjin.kong
 * @date 2020/9/27 20:07
 */
@SpringBootApplication
@EnableEurekaClient
public class MicroServiceCloudEurekaClient8090 {
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudEurekaClient8090.class, args);
    }
}
