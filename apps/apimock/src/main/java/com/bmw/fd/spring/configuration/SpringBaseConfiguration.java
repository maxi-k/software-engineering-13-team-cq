package com.bmw.fd.spring.configuration;

import com.bmw.fd.spring.api.doc.ApidocConfiguration;
import com.bmw.fd.spring.api.doc.ApidocController;
import com.bmw.fd.spring.api.error.ApiExceptionHandler;
import com.bmw.fd.spring.api.security.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;

/**
 * Common technical base configuration class. Enable support for components that are not directly part of the
 * Spring (Boot) ecosystem.
 */
@Configuration
@Import({ApidocConfiguration.class, JwtConfig.class})
public class SpringBaseConfiguration {

    @Bean
    public ApiExceptionHandler springServiceExceptionHandler() {
        return new ApiExceptionHandler();
    }

}
