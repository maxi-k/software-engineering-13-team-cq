package de.unia.se.teamcq.notificationmanagement.service

import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationMESAdapterTest : StringSpec() {

    @InjectMockKs
    private lateinit var notificationMESAdapter: NotificationMESAdapter

    init {
        MockKAnnotations.init(this)

        "SendNotificationForScheduledRule should work" should {

            ReflectionTestUtils.setField(notificationMESAdapter, "authenticationUsername", "name")
            ReflectionTestUtils.setField(notificationMESAdapter, "authenticationPassword", "pw")

            notificationMESAdapter.sendNotification()
        }
    }
}
