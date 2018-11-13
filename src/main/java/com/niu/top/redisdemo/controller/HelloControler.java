package com.niu.top.redisdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hongwei
 * @date 2018/11/9 14:14
 */
@RestController
public class HelloControler {

    @RequestMapping("/hello")
    public String hello(){

        return "hello wolder!";
    }
}
