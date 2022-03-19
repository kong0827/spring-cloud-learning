package com.kxj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xiangjin.kong
 * @date 2020/9/27 19:58
 */
@SpringBootApplication
@EnableEurekaServer
public class MicroServiceCloudEurekaServerApplication8004 {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudEurekaServerApplication8004.class, args);
    }
}
