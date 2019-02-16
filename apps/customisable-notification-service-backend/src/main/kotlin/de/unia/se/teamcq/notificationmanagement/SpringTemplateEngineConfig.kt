package de.unia.se.teamcq.notificationmanagement

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver

@Configuration
class SpringTemplateEngineConfig {

    @Bean
    fun notificationsTextSource(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("notification/NotificationText")
        return messageSource
    }

    @Bean
    fun notificationTemplateEngine(): TemplateEngine {

        val templateEngine = SpringTemplateEngine()

        templateEngine.addTemplateResolver(textTemplateResolver())
        templateEngine.addTemplateResolver(htmlTemplateResolver())
        templateEngine.addTemplateResolver(smsTemplateResolver())

        templateEngine.setTemplateEngineMessageSource(notificationsTextSource())
        return templateEngine
    }

    fun textTemplateResolver(): ITemplateResolver {

        val templateResolver = ClassLoaderTemplateResolver()

        templateResolver.order = 1
        templateResolver.resolvablePatterns = setOf("text/*")
        templateResolver.prefix = "/notification/"
        templateResolver.suffix = ".txt"
        templateResolver.templateMode = TemplateMode.TEXT
        templateResolver.characterEncoding = EMAIL_TEMPLATE_ENCODING
        templateResolver.isCacheable = false

        return templateResolver
    }

    fun htmlTemplateResolver(): ITemplateResolver {

        val templateResolver = ClassLoaderTemplateResolver()

        templateResolver.order = 2
        templateResolver.resolvablePatterns = setOf("html/*")
        templateResolver.prefix = "/notification/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = EMAIL_TEMPLATE_ENCODING
        templateResolver.isCacheable = false

        return templateResolver
    }

    fun smsTemplateResolver(): ITemplateResolver {

        val templateResolver = ClassLoaderTemplateResolver()

        templateResolver.order = 3
        templateResolver.resolvablePatterns = setOf("text/*")
        templateResolver.prefix = "/notification/"
        templateResolver.suffix = ".txt"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = EMAIL_TEMPLATE_ENCODING
        templateResolver.isCacheable = false

        return templateResolver
    }

    companion object {
        const val EMAIL_TEMPLATE_ENCODING = "UTF-8"
    }
}