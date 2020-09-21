package com.kxj.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroServiceCloudEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudEurekaServerApplication.class, args);
    }

}
