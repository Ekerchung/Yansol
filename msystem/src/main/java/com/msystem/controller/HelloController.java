package com.msystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: Eker
 * @date: 2023/4/17 下午 05:46
 * @version: V1.0
 */
@RestController
public class HelloController {
    @GetMapping
    public String hello(){
        return "hello world";
    }
}
