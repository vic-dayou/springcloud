package com.example.webapp2.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
    @Override
    public OAuth2Authentication extractAuthentication(Map<String,?> claims){
        OAuth2Authentication auth2Authentication = super.extractAuthentication(claims);
        auth2Authentication.setDetails(claims);
        return auth2Authentication;
    }
}
