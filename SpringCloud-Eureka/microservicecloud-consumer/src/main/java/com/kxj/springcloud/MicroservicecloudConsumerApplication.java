package com.kxj.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消费者
 */

@SpringBootApplication
public class MicroServiceCloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudConsumerApplication.class, args);
    }

}
