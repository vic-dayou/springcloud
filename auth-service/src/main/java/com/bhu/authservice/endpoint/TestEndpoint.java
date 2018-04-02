package com.bhu.authservice.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestEndpoint {

    @GetMapping("/current")
    public Principal getCurrent(Principal principal){
        return principal;
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable("id") String id){
        return "product:"+id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable("id") String  id){
        return "Order:"+id;
    }
}


