package com.bmw.fd.apimock.boundary.security;

import com.bmw.fd.apimock.boundary.util.RestMock;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class CustomClientDetailsService implements ClientDetailsService {

    private final RestMock credentialsRepository = RestMock.create("data/credentials")
            .withUUIDGenerator()
            .build();
    private final LoginClientCredentials loginClientCredentials;

    @Autowired
    public CustomClientDetailsService(LoginClientCredentials loginClientCredentials) {
        this.loginClientCredentials = loginClientCredentials;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientIdString) {
        BaseClientDetails clientDetails = new BaseClientDetails();

        if (clientIdString.equals("cockpit")) {
            clientDetails.setClientId(clientIdString);
            clientDetails.setClientSecret("{noop}");
            clientDetails.setScope(Collections.singleton("all"));
            return clientDetails;
        }

        if (clientIdString.equals(loginClientCredentials.getId())) {
            clientDetails.setClientId(clientIdString);
            clientDetails.setClientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(loginClientCredentials.getSecret()));
            clientDetails.setScope(Collections.singleton("all"));
            clientDetails.setAuthorizedGrantTypes(List.of("password", "refresh_token"));
            clientDetails.setAccessTokenValiditySeconds(30 * 60);
            return clientDetails;
        }

        UUID clientId = UUID.fromString(clientIdString);
        clientDetails.setClientId(clientId.toString());

        ResponseEntity<ObjectNode> resp = credentialsRepository.get(clientIdString);
        if (!resp.hasBody())
            throw new BadCredentialsException("Unrecognized Fleetdata Client ID");
        String secret = resp.getBody().get("client_secret").asText();
        clientDetails.setClientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(secret));

        clientDetails.setAuthorities(Lists.newArrayList(new SimpleGrantedAuthority("ROLE_FD_SERVER")));
        clientDetails.setScope(Collections.singleton("system"));
        clientDetails.setAuthorizedGrantTypes(List.of("client_credentials", "refresh_token"));

        return clientDetails;
    }
}
