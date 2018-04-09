package com.example.oauthjwt.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class TokenController {

    @Resource(name = "tokenServices")
    private ConsumerTokenServices consumerTokenServices;

    @Resource(name = "tokenStore")
    private TokenStore tokenStore;

    @RequestMapping(value = "/oauth/token/revokeById/{tokenId}", method = RequestMethod.POST)
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable String tokenId) {
        consumerTokenServices.revokeToken(tokenId);
    }

    @RequestMapping(value = "/tokens",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getToken(){
        List<String> tokenValue = new ArrayList<>();
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("foo");
        if (tokens != null){
            for (OAuth2AccessToken token: tokens){
                tokenValue.add(token.getValue());
            }
        }
        return tokenValue;
    }

    @RequestMapping(value = "/oauth/token/revokeRefreshToken/{tokenId}")
    @ResponseBody
    public String revokeRefreshToken(@PathVariable String tokenId){
        if (tokenStore instanceof JdbcTokenStore){
            ((JdbcTokenStore) tokenStore).removeRefreshToken(tokenId);
        }

        return tokenId;
    }


}
