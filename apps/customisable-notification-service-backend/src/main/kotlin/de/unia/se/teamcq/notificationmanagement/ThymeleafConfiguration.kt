package de.unia.se.teamcq.notificationmanagement

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver
import de.unia.se.teamcq.notificationmanagement.service.NotificationTextService


/**
 * A configuration for the [TemplateEngine] used in [NotificationTextService].
 *
 * Usually, you don't need additional configuration to use Thymeleaf in Spring Boot
 * applications, just depending on spring-boot-starter-thymeleaf is enough
 * thanks to Spring Boot auto configuration. Unfortunately, the [TemplateEngine]
 * resulting from the auto configuration can only resolve html templates.
 * To address that, the templateEngine function here provides a named [Bean] that
 * is able to resolve both html and text templates based on some custom folder structure.
 */
@Configuration
class ThymeleafConfiguration {

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