package com.example.publisher.services;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;

public interface Sender {

    @Output(Sink.INPUT)
    MessageChannel output();
}
