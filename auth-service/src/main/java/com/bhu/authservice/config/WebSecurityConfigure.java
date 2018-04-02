package com.bhu.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService(){
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
////        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
////        return manager;
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//        jdbcUserDetailsManager.createUser(User.withUsername("admin").password("123456").authorities("ADMIN").build());
//        jdbcUserDetailsManager.createUser(User.withUsername("user").password("123456").authorities("USER").build());
//        return jdbcUserDetailsManager;
//    }
    @Configuration
    @Order(-20)
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
          auth.jdbcAuthentication().dataSource(dataSource)
                   .withUser("admin").password("admin").roles("ADMIN")
                   .and()
                   .withUser("user").password("user").roles("USER")
         ;
     }
}

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/uaa/oauth/**").permitAll()
                ;
    }
}
