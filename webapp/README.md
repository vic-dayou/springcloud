# Feign 声明式服务调用
## 什么是声明式？
声明式就是只需要声明代码表达在哪里（where）和做什么（what），不需要关心怎么做（how），Aspect Oriented Programming(面向切面编程)，
AOP就是一种Declarative Programming(声明式编程)。
与此对应的就是Imperative Programming(命令式编程)，它只需要声明代码表达在哪里（where）和做什么（what）但是要实现怎么做（how）

>Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并用注解的方式来配置它，即可完成对服
务提供方的接口绑定，简化了在使用Ribbon时自行封装服务调用客户端的开发量。
>
>Feign具有可插拔的注解特性，包括Feign注解和JAX-RS注解，同时也扩展了对SpringMVC的注解支持。Feign支持可插拔的编码器和解码器，默认集
成了Ribbon和Hystrix，并和Eureka结合，默认实现了负载均衡的效果。

## feign的使用简介
只需要创建一个接口并用注解的方式来配置它，即可完成对服务的调用
```
@FeignClient(name = "webapp1",fallback = FeignFallback.class)
public interface FeignServer {

    @RequestMapping(value = "/webapp1/get",method = RequestMethod.GET)
    String helloService(@RequestParam("name") String  name);

    @RequestMapping(value = "/webapp1/header",method = RequestMethod.HEAD)
    String helloService(@RequestHeader("name") String name,@RequestHeader("password") String password);

    @RequestMapping(value = "/webapp1/post",method = RequestMethod.POST)
    String helloService(@RequestBody User user);
}
```
## feign传递OAuth2 access_token
只需要添加下面两个Bean，feign自动实现Request的拦截并在请求中添加access_token实现Token的传递
```
  @Bean
    ClientCredentialsResourceDetails clientCredentialsResourceDetails(){
        return  new ClientCredentialsResourceDetails();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(@Qualifier("oauth2ClientContext") OAuth2ClientContext auth2ClientContext,
                                                            ClientCredentialsResourceDetails resourceDetails) {
        return new OAuth2FeignRequestInterceptor(auth2ClientContext, resourceDetails);
    }
```
其实从这个webapp这个例子看，其中webapp1和webapp2中有很多代码重复的部分，看起来一点也不简洁优雅，这个时候就可以使用feign的继承特
性了，我们只需要把它们中重复的代码抽取出来，同时使用Maven私有仓库就可以轻松实现代码的共享。但是这样就又形成了对共享接口的依赖，
那么接口变动就会对项目构建造成影响，例如服务提供者修改了一个接口的定义，那么就会直接造成客户端构建失败。所以看情况而定啦！
