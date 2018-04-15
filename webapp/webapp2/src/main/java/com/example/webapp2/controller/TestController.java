package com.example.webapp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    FeignServer feignServer;

    @RequestMapping(value = "/testGet",method = RequestMethod.GET)
    String testService(@RequestParam("name") String name){
        return  feignServer.helloService(name);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    String test1(){
        return "test!~";
    }
}
