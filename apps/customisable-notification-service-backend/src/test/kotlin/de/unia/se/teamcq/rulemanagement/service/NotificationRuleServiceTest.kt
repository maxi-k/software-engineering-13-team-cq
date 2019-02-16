package de.unia.se.teamcq.rulemanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
import de.unia.se.teamcq.scheduling.service.INotificationScheduler
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verify
import io.mockk.just
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationRuleServiceTest : StringSpec() {

    @MockK
    private lateinit var notificationRuleRepository: INotificationRuleRepository

    @MockK
    private lateinit var notificationScheduler: INotificationScheduler

    @InjectMockKs
    private lateinit var notificationRuleService: NotificationRuleService

    init {
        MockKAnnotations.init(this)

        val mockedNotificationRule = getTestNotificationRuleModel()

        "GetNotificationRulesForUser should work" {
            every { notificationRuleRepository.getAllNotificationRulesForUser(any()) } returns listOf(mockedNotificationRule)

            val notificationRules = notificationRuleService.getNotificationRulesForUser("test")
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.getAllNotificationRulesForUser(any())
            }
        }

        "GetNotificationRule should work" {
            every { notificationRuleRepository.getNotificationRule(any()) } returns mockedNotificationRule

            val notificationRules = notificationRuleService.getNotificationRule(1)
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.getNotificationRule(any())
            }
        }

        "CreateNotificationRule should work" {
            every { notificationRuleRepository.createNotificationRule(any()) } returns mockedNotificationRule
            every { notificationScheduler.updateNotificationRuleScheduleAsNecessary(any()) } just Runs

            val notificationRules = notificationRuleService.createNotificationRule("test", mockedNotificationRule)
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.createNotificationRule(any())
                notificationScheduler.updateNotificationRuleScheduleAsNecessary(any())
            }
        }

        "UpdateNotificationRule should work" {
            clearMocks(notificationScheduler)

            every { notificationRuleRepository.updateNotificationRule(any()) } returns mockedNotificationRule
            every { notificationScheduler.updateNotificationRuleScheduleAsNecessary(any()) } just Runs

            val notificationRules = notificationRuleService.updateNotificationRule(mockedNotificationRule)
            notificationRules shouldBe notificationRules

            verify(exactly = 1) {
                notificationRuleRepository.updateNotificationRule(any())
                notificationScheduler.updateNotificationRuleScheduleAsNecessary(any())
            }
        }

        "DeleteNotificationRule should work" {
            every { notificationRuleRepository.deleteNotificationRule(1) } just Runs
            every { notificationScheduler.deleteNotificationRuleSchedule(1) } just Runs

            notificationRuleService.deleteNotificationRule(1)

            verify(exactly = 1) {
                notificationRuleRepository.deleteNotificationRule(1)
                notificationScheduler.deleteNotificationRuleSchedule((1))
            }
        }
    }
}
