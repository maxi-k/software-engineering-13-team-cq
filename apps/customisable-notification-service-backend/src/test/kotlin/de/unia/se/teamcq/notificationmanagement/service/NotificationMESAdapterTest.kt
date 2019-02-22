package de.unia.se.teamcq.notificationmanagement.service

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NotificationMESAdapterTest : StringSpec() {

    @Autowired
    private lateinit var notificationMESAdapter: NotificationMESAdapter

    init {
        MockKAnnotations.init(this)

        "SendNotification should work" {

            ReflectionTestUtils.setField(notificationMESAdapter, "disableNotifications", false)
            ReflectionTestUtils.setField(notificationMESAdapter, "authenticationUsername", "admin")
            ReflectionTestUtils.setField(notificationMESAdapter, "authenticationPassword", "fd123!")

            shouldThrow<Exception> {
                notificationMESAdapter.sendNotification()
            }
        }
    }
}
