package com.example.publisher.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding({Sender.class,Sinker.class})
public class WebServiceImpl implements WebService {


    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceImpl.class);
    private final Sender sender;

    @Autowired
    public WebServiceImpl(Sender sender) {
        this.sender = sender;
    }

    @Override
    public int output(String message) {
        sender.output().send(MessageBuilder.withPayload(message).build());
        LOGGER.info(" [*]-------->publish message to channel "+message);
        return 0;
    }

    @StreamListener(Processor.OUTPUT)
    public void receive(Object payload) {
        LOGGER.info(" [x] callback" + payload);
    }

}
