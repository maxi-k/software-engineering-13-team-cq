package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.security.service.IAuthenticationTokenAdapter
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ByteArrayResource
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NotificationMESAdapterTest : StringSpec() {

    @Autowired
    private lateinit var authenticationTokenAdapter: IAuthenticationTokenAdapter

    @Autowired
    private lateinit var notificationMESAdapter: NotificationMESAdapter

    init {
        MockKAnnotations.init(this)

        "SendNotification should work when a Mail API-Service is running".config(enabled = false) {

            ReflectionTestUtils.setField(notificationMESAdapter, "disableNotifications", false)
            ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationUsername", "admin")
            ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationPassword", "fd123!")

            notificationMESAdapter.sendNotification(
                    "test@example.de",
                    "Subject",
                    "text",
                    ByteArrayResource(ByteArray(0)))
        }
    }
}
