package com.example.publisher;

import com.example.publisher.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class PublisherApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublisherApplication.class);


	//测试ajax post
	@RequestMapping(value = "/abc",method = RequestMethod.POST)
	@ResponseBody
	public User returnAbc(@RequestBody User user) {
		LOGGER.info(" [*]--------->>>: "+user.getName());
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(PublisherApplication.class, args);
	}
}
