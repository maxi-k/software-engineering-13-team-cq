package com.bmw.fd.spring.configuration;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Indicates that all non-spring-internal components must be enabled in the services.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
@Documented
@Import({SpringBaseConfiguration.class})
public @interface EnableSpringBase {
}
