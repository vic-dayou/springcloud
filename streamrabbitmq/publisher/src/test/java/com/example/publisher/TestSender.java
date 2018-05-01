package com.example.publisher;


import com.example.publisher.services.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableBinding({Sender.class})
public class TestSender {

    @Autowired
   private Sender sender;


    @Test
    public void context() {
        sender.output().send(MessageBuilder.withPayload("abc").build());
    }

}
