package com.bmw.fd.apimock.boundary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Collections;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenConverter tokenConverter;
    private final TokenStore tokenStore;
    private final LoginClientCredentials loginClientCredentials;
    private final CustomClientDetailsService customClientDetailsService;
//    private final UserDetailsService userDetailsService;

    @Autowired
    public OAuth2AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                           JwtAccessTokenConverter tokenConverter,
                                           TokenStore tokenStore,
                                           LoginClientCredentials loginClientCredentials,
                                           CustomClientDetailsService customClientDetailsService
//                                           UserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenConverter = tokenConverter;
        this.tokenStore = tokenStore;
        this.loginClientCredentials = loginClientCredentials;
        this.customClientDetailsService = customClientDetailsService;
//        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(tokenConverter));

        endpoints.tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                //.userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);

//        clients.inMemory()
//                .withClient("cockpit")//loginClientCredentials.getId())
//                .authorizedGrantTypes("password")
//                .secret("{noop}")// + loginClientCredentials.getSecret())
//                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients();
    }
}