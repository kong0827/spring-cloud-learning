package com.kxj.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroServiceCloudEurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudEurekaClientApplication.class, args);
    }

}
