package com.bhu.servicesa.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "services-b")
public interface ServiceBClient {

    @GetMapping(value = "/")
    String printServiceB();

    @Component
    class ServiceBClientFallback implements ServiceBClient{
        private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBClientFallback.class);
        @Override
        public String printServiceB(){
            LOGGER.info("Exception->fallback");
            return "Service B failed!";
        }
    }
}
