package com.bmw.fd.apimock.boundary.security;

import com.bmw.fd.apimock.boundary.util.JsonStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class MockAuthenticationProvider implements AuthenticationProvider {

    private final JsonStore credentials = new JsonStore("data/credentials", "id");

    private final Counter successfulLogins;
    private final Counter failedLogins;
    private final Counter adminLogins;

    public MockAuthenticationProvider(MeterRegistry meterRegistry) {
        this.successfulLogins = meterRegistry.counter("bmwfd_logins_total", "result", "success");
        this.failedLogins = meterRegistry.counter("bmwfd_logins_total", "result", "failure");
        this.adminLogins = meterRegistry.counter("bmwfd_login_admin_total");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var login = authentication.getName();
        Optional<ObjectNode> creds = credentials.findFirst(a -> a.has("login") && login.equals(a.get("login").textValue()));
        JsonNode secret = creds.orElseThrow(() -> {failedLogins.increment(); return new BadCredentialsException("Account does not exist");});

        var password = authentication.getCredentials().toString();
        if (password.equals(secret.get("password").textValue())) {
            successfulLogins.increment();
            List<GrantedAuthority> roles = new ArrayList<>();
            secret.get("roles").forEach(role -> roles.add(new SimpleGrantedAuthority(role.textValue())));
            if(StreamSupport.stream(secret.get("roles").spliterator(),false).anyMatch(r->r.textValue().equals("FD_ALL")))
                adminLogins.increment();
            return new UsernamePasswordAuthenticationToken(
                    secret.get("id").textValue(),
                    authentication.getCredentials(),
                    roles);
        }
        else {
            failedLogins.increment();
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
