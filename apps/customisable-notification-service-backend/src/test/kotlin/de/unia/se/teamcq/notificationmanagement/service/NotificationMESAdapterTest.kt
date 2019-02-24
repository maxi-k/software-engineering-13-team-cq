package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.security.service.IAuthenticationTokenService
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NotificationMESAdapterTest : StringSpec() {

    @Autowired
    private lateinit var authenticationTokenService: IAuthenticationTokenService

    @Autowired
    private lateinit var notificationMESAdapter: NotificationMESAdapter

    init {
        MockKAnnotations.init(this)

        "SendNotification should work when a Mail API-Service is running".config(enabled = false) {

            ReflectionTestUtils.setField(notificationMESAdapter, "disableNotifications", false)
            ReflectionTestUtils.setField(authenticationTokenService, "authenticationUsername", "admin")
            ReflectionTestUtils.setField(authenticationTokenService, "authenticationPassword", "fd123!")

            notificationMESAdapter.sendNotification("test@example.de", "Subject", "text", listOf())
        }
    }
}
