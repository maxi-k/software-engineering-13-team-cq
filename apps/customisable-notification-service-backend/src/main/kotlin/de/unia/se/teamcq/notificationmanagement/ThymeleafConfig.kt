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
class ThymeleafConfig {

    @Bean
    fun notificationsTextSource(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("notification/i18n/messages")
        return messageSource
    }

    @Bean(name = ["notificationTemplateEngine"])
    fun templateEngine(): TemplateEngine {

        val templateEngine = SpringTemplateEngine()

        templateEngine.addTemplateResolver(textTemplateResolver())
        templateEngine.addTemplateResolver(htmlTemplateResolver())

        templateEngine.setTemplateEngineMessageSource(notificationsTextSource())
        return templateEngine
    }

    fun textTemplateResolver(): ITemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.order = 1
        templateResolver.prefix = "/notification/text/"
        templateResolver.suffix = ".txt"
        templateResolver.templateMode = TemplateMode.TEXT
        templateResolver.characterEncoding = "UTF8"
        templateResolver.checkExistence = true
        templateResolver.isCacheable = false
        return templateResolver
    }

    fun htmlTemplateResolver(): ITemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.order = 2
        templateResolver.prefix = "/notification/html/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = "UTF8"
        templateResolver.checkExistence = true
        templateResolver.isCacheable = false
        return templateResolver
    }
}
