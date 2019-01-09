package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
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
    private lateinit var notificationRuleRepository: INotificationRuleRepository

    @InjectMockKs
    private lateinit var notificationRuleService: NotificationRuleService

    init {
        MockKAnnotations.init(this)

        "get all should work" {
            every { notificationRuleRepository.getAllNotificationRulesForUser(any()) } returns emptyList()

            notificationRuleService.getNotificationRulesForUser("test")
        }
    }
}
