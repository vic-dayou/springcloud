# Spring Cloud Stream
[spring cloud stream](https://docs.spring.io/spring-cloud-stream/docs/Ditmars.SR3/reference/htmlsingle/#_rabbitmq_binder)
Spring Cloud Stream是构建消息驱动的微服务应用程序的框架。 Spring Cloud Stream基于Spring Boot构建独立的生产级Spring应用程序，并使用Spring Integration为消息代理提供连接。它提供了来自多个供应商的中间件的自定义配置，介绍了persistent publish-subscribe semantics, consumer groups,和partitions的概念。简单来说，Spring Cloud Stream 本质上就是整合了Spring Boot和Spring Integration，实现了一套轻量级的消息驱动的微服务框架。
## 核心注解
* @EnableBinding
* @Input
* @Output
* @StreamListener
* @SendTo
### @EnableBinding
@EnableBinding 注解将一个或多个接口（被声明为是input/output channel）作为参数，Spring Cloud Stream自带了Sink，Source和Processor接口；当然也可以自己创建。效果一样。
```
public interface Sink {
  String INPUT = "input";

  @Input(Sink.INPUT)
  SubscribableChannel input();
}
```
```
public interface Source {
	String OUTPUT = "output";

	@Output(Source.OUTPUT)
	MessageChannel output();
}
```
```
public interface Processor extends Source, Sink{
}
```
### @Input/@Output
一个spring cloud stream application可以有任意多的@Input和@Output
@Input 注解将一个channel名作为参数，如果没有提供则会使用方法名作为channel名。被@Input注解的方法Spring Cloud Stream会为项目自动创建相应的以channel Bean，随后的消息接收则会通过这个Channel在Application和Broker之间进行。如果项目中要创建两个相同的channel名的Bean，则会出现Exception ：Bean already exists。[这个是我经历的问题](https://stackoverflow.com/questions/49993548/spring-cloud-stream-inputsink-input-and-outputsink-input-have-bean-already)
@Output 同@Input，只是使用@Output注解的方法Spring cloud Stream会创建相应的输出channel Bean。
### @StreamListener/@SendTo
使用@StreamListener注解在一个方法上，Spring Cloud Stream自动添加一个监听机制在该方法，自动监听@StreamListener监听的channel。然而只使用@StreamListener注解的方法不能有返回值，若要返回执行结果则添加@SendTo注解，将返回值发布到@SentTo提供的输出Channel。
```
@EnableBinding({Processor.class})
public class Receiver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Object receive(Object payload) {
        logger.info(" [*] Receive: " + payload);
        return " [*] Received :" + payload;
    }
}
```




