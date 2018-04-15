package com.example.webapp2.config;

import feign.RequestInterceptor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity
public class OAuthResourceServer extends ResourceServerConfigurerAdapter {

    @Bean
    ClientCredentialsResourceDetails clientCredentialsResourceDetails(){
        return  new ClientCredentialsResourceDetails();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(@Qualifier("oauth2ClientContext") OAuth2ClientContext auth2ClientContext,
                                                            ClientCredentialsResourceDetails resourceDetails) {
        return new OAuth2FeignRequestInterceptor(auth2ClientContext, resourceDetails);
    }


    private final static String RESOURCE_ID = "test";

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
//                antMatchers()不允许有空的模式匹配，若为空则出现:org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'springSecurityFilterChain' defined in class path resource [org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.class]:
//                .authorizeRequests().antMatchers("/**").authenticated();
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer resource) {
        resource.tokenServices(tokenServices())
                .resourceId(RESOURCE_ID);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Autowired
    CustomAccessTokenConverter customAccessTokenConverter;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(customAccessTokenConverter);

        final Resource resource = new ClassPathResource("public");
        String publicKey;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
            converter.setVerifierKey(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return converter;

    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }
}
