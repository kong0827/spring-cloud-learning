package com.kxj.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2022/3/21 00:23
 * @desc
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(@RequestParam String content) {
        return content;
    }
}
