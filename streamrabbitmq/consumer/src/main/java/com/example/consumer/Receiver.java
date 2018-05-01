package com.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding({Processor.class})
public class Receiver {

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Object receive(Object payload) {
        logger.info(" [x] Receive: " + payload);
        return " [*] Received :" + payload;
    }
}
