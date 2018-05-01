package com.example.publisher.services;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.SubscribableChannel;

public interface Sinker {

    @Input(Processor.OUTPUT)
    SubscribableChannel input();
}

