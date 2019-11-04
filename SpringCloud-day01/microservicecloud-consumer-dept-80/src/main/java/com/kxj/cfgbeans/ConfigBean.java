package com.kxj.cfgbeans;

/**
 * @ClassName ConfigBean
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/11/4 10:43
 * @Version 1.0
 **/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

