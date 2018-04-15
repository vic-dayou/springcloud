package com.example.webapp2.controller;

import com.example.webapp2.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "webapp1",fallback = FeignFallback.class)
public interface FeignServer {

    @RequestMapping(value = "/webapp1/get",method = RequestMethod.GET)
    String helloService(@RequestParam("name") String  name);

    @RequestMapping(value = "/webapp1/header",method = RequestMethod.HEAD)
    String helloService(@RequestHeader("name") String name,@RequestHeader("password") String password);

    @RequestMapping(value = "/webapp1/post",method = RequestMethod.POST)
    String helloService(@RequestBody User user);
}
