package com.example.webapp1.controller;

import com.example.webapp1.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class HelloServiceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    private void sleep(String methodName){
        int sleepMinTime = new Random().nextInt(3000);
        logger.info("helloService: <"+methodName+">sleepTime: "+sleepMinTime);
//        try {
//            Thread.sleep(sleepMinTime);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    @RequestMapping(value = "/webapp1/get", method = RequestMethod.GET)
    public String helloService(@RequestParam String  name){
        sleep("GET");
        return "HelloServiceImpl: "+name;
    }

    @RequestMapping(value = "/webapp1/header",method = RequestMethod.HEAD)
    public String helloService(@RequestHeader String name,@RequestHeader String password){
        sleep("HEADER");
        return "helloServiceImpl:"+name+"-password:"+password;
    }

    @RequestMapping(value = "/webapp1/post",method = RequestMethod.POST)
    public String helloService(@RequestBody User user){
        sleep("POST");
        return user.toString();
    }

}
