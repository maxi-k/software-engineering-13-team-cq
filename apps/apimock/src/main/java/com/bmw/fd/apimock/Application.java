package com.bmw.fd.apimock;

import com.bmw.fd.spring.api.doc.ApidocConfiguration;
import com.bmw.fd.spring.api.error.ApiExceptionHandler;
import com.bmw.fd.spring.api.security.JwtConfig;
import com.bmw.fd.spring.configuration.EnableSpringBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.UUID;


@SpringBootApplication
//@EnableSpringBase
@Import({ApidocConfiguration.class, JwtConfig.class})
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApiExceptionHandler springServiceExceptionHandler() {
        return new ApiExceptionHandler();
    }
}

