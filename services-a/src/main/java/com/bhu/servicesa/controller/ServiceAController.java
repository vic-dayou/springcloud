package com.bhu.servicesa.controller;

import com.bhu.servicesa.client.ServiceBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RefreshScope
@RestController
public class ServiceAController {


    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    ServiceBClient serviceBClient;

    @GetMapping(value = "/getServiceA")
    public String printServiceA(){
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        return instance.getServiceId()+" ( "+instance.getHost()+instance.getPort()+" ) "+serviceBClient.printServiceB();
    }

    @GetMapping("/test")
    public String getTest(){
        return "test";
    }

    @GetMapping(path = "/current")
    public Principal getCurrentAccount(Principal principal){
        return principal;
    }
}
