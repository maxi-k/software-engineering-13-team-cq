package de.unia.se.teamcq.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class CorsConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        // FIXME: Change origins to something more restrictive,
        // probably based on environment variables. Required because
        // heroku deployment runs from multiple instances (and URLs)
        registry.addMapping("/**")
                .allowedOrigins("*")
    }
}
