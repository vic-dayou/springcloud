package com.bhu.servicesa;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCircuitBreaker
@RestController
@EnableDiscoveryClient
@FeignClient
@EnableOAuth2Client
public class ServicesAApplication {


	//hystrix的一个调用接口，目的是测试/hystrix.stream,如果没有调用hystrix的任意一个接口
	//则会出现访问/hystrix.stream 一直处于ping，而没有数据显示
	@GetMapping("/hello")
	@HystrixCommand(fallbackMethod = "defaultFall")
	public String hello(){
		return "hello,World!";
	}

	public String defaultFall(){
		return "error";
	}

	public static void main(String[] args) {
		SpringApplication.run(ServicesAApplication.class, args);
	}
}
