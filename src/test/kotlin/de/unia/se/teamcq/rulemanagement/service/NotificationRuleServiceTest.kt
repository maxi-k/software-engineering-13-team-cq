package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verify
import io.mockk.just
import io.mockk.Runs
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

        val mockedNotificationRule = getTestNotificationRuleModel()

        "getNotificationRulesForUser should work" {
            every { notificationRuleRepository.getAllNotificationRulesForUser(any()) } returns listOf(mockedNotificationRule)

            val notificationRules = notificationRuleService.getNotificationRulesForUser("test")
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.getAllNotificationRulesForUser(any())
            }
        }

        "getNotificationRule should work" {
            every { notificationRuleRepository.getNotificationRule(any()) } returns mockedNotificationRule

            val notificationRules = notificationRuleService.getNotificationRule(1)
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.getNotificationRule(any())
            }
        }

        "createNotificationRule should work" {
            every { notificationRuleRepository.createNotificationRule(any()) } returns mockedNotificationRule

            val notificationRules = notificationRuleService.createNotificationRule("test", mockedNotificationRule)
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.createNotificationRule(any())
            }
        }

        "updateNotificationRule should work" {
            every { notificationRuleRepository.updateNotificationRule(any()) } returns mockedNotificationRule

            val notificationRules = notificationRuleService.updateNotificationRule(mockedNotificationRule)
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.updateNotificationRule(any())
            }
        }

        "deleteNotificationRule should work" {
            every { notificationRuleRepository.deleteNotificationRule(any()) } just Runs

            notificationRuleService.deleteNotificationRule(1)

            verify(exactly = 1) {
                notificationRuleRepository.deleteNotificationRule(1)
            }
        }
    }
}
