package com.example.webapp2.controller;

import com.example.webapp2.entity.User;

public class FeignFallback implements FeignServer {
    @Override
    public String helloService(String name) {
        return "get error";
    }

    @Override
    public String helloService(String name, String password) {
        return "header error";
    }

    @Override
    public String helloService(User user) {
        return "post error";
    }
}
