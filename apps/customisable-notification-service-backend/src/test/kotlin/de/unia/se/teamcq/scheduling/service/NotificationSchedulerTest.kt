package de.unia.se.teamcq.scheduling.service

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.spyk
import io.mockk.verify
import org.quartz.Scheduler
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationSchedulerTest : StringSpec() {

    @MockK
    private lateinit var scheduler: Scheduler

    @InjectMockKs
    private lateinit var notificationScheduler: NotificationScheduler

    init {
        MockKAnnotations.init(this)

        notificationScheduler = spyk(notificationScheduler, recordPrivateCalls = true)

        "UpdateNotificationRuleScheduleAsNecessary" should {

            "Not fail and do nothing if notificationRule is null" should {
                notificationScheduler.updateNotificationRuleScheduleAsNecessary(null)
            }

            "Schedule NotificationRules and delete old entries" should {

                every { scheduler.scheduleJob(any(), any()) } returns null

                every { notificationScheduler.deleteNotificationRuleSchedule(any()) } just Runs

                val notificationRule = getTestNotificationRuleModel()

                notificationScheduler.updateNotificationRuleScheduleAsNecessary(notificationRule)

                verify(exactly = 1) {
                    notificationScheduler.deleteNotificationRuleSchedule(notificationRule.ruleId!!)
                    scheduler.scheduleJob(any(), any())
                }
            }
        }

        "DeleteNotificationRuleSchedule" should {
            "Not fail and do nothing if no such job exists" {
                clearMocks(scheduler, notificationScheduler)

                every { scheduler.deleteJob(any()) } returns false
                notificationScheduler.deleteNotificationRuleSchedule(-1)
            }

            "Delete existing jobs" {
                clearMocks(scheduler, notificationScheduler)

                every { scheduler.deleteJob(any()) } returns true
                notificationScheduler.deleteNotificationRuleSchedule(1)

                verify(exactly = 1) {
                    scheduler.deleteJob(any())
                }
            }
        }

        "ScheduleVehicleStateDataImport should schedule the Data Import" {
            clearMocks(scheduler, notificationScheduler)

            every { scheduler.scheduleJob(any(), any(), any()) } just Runs
            ReflectionTestUtils.setField(notificationScheduler, "dataImportCronString", "0 0/1 * * * ?")

            notificationScheduler.scheduleVehicleStateDataImport()

            verify(exactly = 1) {
                scheduler.scheduleJob(any(), any(), any())
            }
        }
    }
}
