package de.unia.se.teamcq.security.service

import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AuthenticationTokenAdapterTest : StringSpec() {

    @Autowired
    private lateinit var authenticationTokenAdapter: AuthenticationTokenAdapter

    init {
        MockKAnnotations.init(this)

        "SendNotification should work when the API-Mock is running".config(enabled = true) {

            ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationUsername", "admin")
            ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationPassword", "fd123!")

            val (header, token) = authenticationTokenAdapter.getAuthenticationHeader()
            header shouldNotBe null
            token shouldNotBe null
        }
    }
}
