package com.example.publisher.dynamicBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/*
*
* The BinderAwareChannelResolver is a general purpose Spring Integration DestinationResolver and can be injected in other components.
* For example, in a router using a SpEL expression based on the target field of an incoming JSON message
*
* */


@EnableBinding
@Controller
public class DynamicBoundDestinations2 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BinderAwareChannelResolver resolver;
    private ExpressionEvaluatingRouter router;


    @Autowired
    public DynamicBoundDestinations2(BinderAwareChannelResolver resolver) {
        this.resolver = resolver;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = "*/*")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRequest(@RequestBody String body, @RequestHeader(HttpHeaders.CONTENT_TYPE) Object contentType) {
        sendMessage(body,contentType);
    }

    private void sendMessage(String body, Object contentType) {
        logger.info(" [*] will create channel "+router.getDefaultOutputChannel()+" publish message" + body);
        routerChannel().send(MessageBuilder.createMessage(body,
                new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE,
                        contentType))));

    }


    @Bean(name = "routerChannel")
    public MessageChannel routerChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "routerChannel")
    public ExpressionEvaluatingRouter router() {
        router = new ExpressionEvaluatingRouter(new SpelExpressionParser().parseExpression("payload.target"));
        router.setDefaultOutputChannelName("default-output");
        router.setChannelResolver(resolver);
        return router;
    }
}
