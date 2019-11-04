package com.kxj; /**
 * @ClassName com.kxj.EurekaServer7001_App
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/11/4 10:58
 * @Version 1.0
 **/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer     //EurekaServer服务器端启动类,接受其它微服务注册进来
public class EurekaServer7001_App {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7001_App.class, args);
    }
}



