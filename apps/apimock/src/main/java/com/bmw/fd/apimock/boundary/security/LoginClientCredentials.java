package com.bmw.fd.apimock.boundary.security;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Client credentials to be used by the Login Controller to do the POST to /oauth/token
 */
@Service
public class LoginClientCredentials {

    private final String id = UUID.randomUUID().toString();
    private final String secret = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }
}
