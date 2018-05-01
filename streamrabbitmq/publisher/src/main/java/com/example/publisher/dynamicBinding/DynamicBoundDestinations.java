package com.example.publisher.dynamicBinding;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
/*
* Besides the channels defined via @EnableBinding,
* Spring Cloud Stream allows applications to send messages to dynamically bound destinations.
* This is useful, for example, when the target destination needs to be determined at runtime.
* Applications can do so by using the BinderAwareChannelResolver bean,
* registered automatically by the @EnableBinding annotation.
*The property 'spring.cloud.stream.dynamicDestinations' can be used for restricting the dynamic destination names to a set known beforehand (whitelisting). If the property is not set, any destination can be bound dynamicaly.
*The BinderAwareChannelResolver can be used directly as in the following example, in which a REST controller uses a path variable to decide the target channel.
*
*curl -H "Content-Type: application/json" -X POST -d "customer-1" http://localhost:8082/customers
*curl -H "Content-Type: application/json" -X POST -d "order-1" http://localhost:8082/orders
*
* The destinations 'customers' and 'orders' are created in the broker (for example: exchange in case of Rabbit or topic in case of Kafka) with the names 'customers' and 'orders', and the data is published to the appropriate destinations.
* */

@EnableBinding
@Controller
public class DynamicBoundDestinations {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BinderAwareChannelResolver resolver;

    @Autowired
    public DynamicBoundDestinations(BinderAwareChannelResolver resolver) {
        this.resolver = resolver;
    }


    @RequestMapping(path = "/{target}", method = RequestMethod.POST, consumes = "*/*")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRequest(@RequestBody String body, @PathVariable("target") String target,
                              @RequestHeader(HttpHeaders.CONTENT_TYPE) Object contentType) {
        logger.info(" [*] will create new channel "+target+",receive the message " + body);
        sendMessage(body,target,contentType);
    }

    private void sendMessage(String body, String target, Object contentType) {
        resolver.resolveDestination(target).send(MessageBuilder.createMessage(body,
                new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE,
                        contentType))));


    }

}
