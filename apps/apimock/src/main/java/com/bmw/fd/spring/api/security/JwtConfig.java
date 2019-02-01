package com.bmw.fd.spring.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Configuration
public class JwtConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtConfig.class);

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter accessTokenConverter) {
        return new JwtTokenStore(accessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(
            @Value("${jwt.signing-key:#{null}}") String signingKey,
            @Value("${jwt.verifier-key:#{null}}") String verifierKey) throws IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        if (signingKey != null || verifierKey != null) {
            if (signingKey != null) {
                converter.setSigningKey(loadKey(signingKey));
            }
            if (verifierKey != null) {
                converter.setVerifierKey(loadKey(verifierKey));
            }
        } else {
            String randomSigningKey = UUID.randomUUID().toString();
            LOGGER.warn("No jwt signing or verifier key set - using random signing key: {}", randomSigningKey);
            converter.setSigningKey(randomSigningKey);
        }
        return converter;
    }

    private String loadKey(String key) throws IOException {
        if (key.startsWith("file:")) {
            try (InputStream is = new FileInputStream(new File(URI.create(key)))) {
                return StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            }
        } else {
            return key;
        }
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(TokenStore tokenStore) {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);

        return defaultTokenServices;
    }
}
