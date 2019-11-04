package com.kxj.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroservicecloudEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicecloudEurekaServerApplication.class, args);
    }

}
