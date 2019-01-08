package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntityRepository
import de.unia.se.teamcq.rulemanagement.mapping.NotificationRuleMapper
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationRuleServiceTest : StringSpec() {

    @MockK
    private lateinit var notificationRuleEntityRepository: NotificationRuleEntityRepository

    @MockK
    private lateinit var notificationRuleMapper: NotificationRuleMapper

    @InjectMockKs
    private lateinit var notificationRuleService: NotificationRuleService

    init {
        MockKAnnotations.init(this)

        "get all should work" {
            every { notificationRuleEntityRepository.findAll() } returns emptyList()

            notificationRuleService.getNotificationRulesForUser("test")
        }
    }
}
