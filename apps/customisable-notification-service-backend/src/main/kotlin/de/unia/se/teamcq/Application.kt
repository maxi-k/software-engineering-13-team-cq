package de.unia.se.teamcq

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean


@SpringBootApplication
@EnableScheduling
class Application : WebMvcConfigurer {

    fun main(args: Array<String>) {
        SpringApplication.run(Application::class.java, *args)
    }

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("i18n/")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }
}
