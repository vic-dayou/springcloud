package com.example.publisher.controller;

import com.example.publisher.entity.User;
import com.example.publisher.services.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WebService webService;

    @Autowired
    public WebController(WebService webService) {
        this.webService = webService;
    }


    @GetMapping("/")
    public String show() {
        return "show";
    }


    @PostMapping(value = "/orders",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,String> order(@RequestBody User user) {
        webService.output(user.getName());
        Map<String, String> map = new HashMap<>();
        map.put("msg","You have completed the subscription");
        map.put("errno","0");
        return map;
    }


}
