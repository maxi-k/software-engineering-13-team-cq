package de.unia.se.teamcq.rulemanagement

import com.google.gson.Gson
import de.unia.se.teamcq.TestUtils.buildMockMvc
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.security.JwtTokenAuthenticationFilter
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationRuleCRUDIntegrationTest : StringSpec() {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var jwtTokenAuthenticationFilter: JwtTokenAuthenticationFilter

    private val gson = Gson()

    init {
        MockKAnnotations.init(this)

        "Fail without Authorization" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val notificationRuleToCreate = NotificationRuleDto(0, "name", "description")

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(notificationRuleToCreate)))
                    .andExpect(status().isUnauthorized)
        }

        "Fail for invalid Authentication Token" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val notificationRuleToCreate = NotificationRuleDto(0, "name", "description")

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer faketoken")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(notificationRuleToCreate)))
                    .andExpect(status().isUnauthorized)
        }

        "Create and delete NotificationRules with valid token" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val accessToken = jwtTokenAuthenticationFilter.createToken("username")

            val notificationRuleToCreate = NotificationRuleDto(0, "name", "description")

            var returnedNotificationRule: NotificationRuleDto? = null

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(notificationRuleToCreate)))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        returnedNotificationRule = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRuleDto::class.java)

                        returnedNotificationRule!!.id!!.shouldBeGreaterThanOrEqual(1)
                        returnedNotificationRule!!.name shouldBe "name"
                        returnedNotificationRule!!.description shouldBe "description"
                    }

            val ruleId = returnedNotificationRule!!.id

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/$ruleId")
                    .header("Authorization", "Bearer $accessToken")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val retrievedNotificationRule = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRule::class.java)

                        retrievedNotificationRule!!.id!!.shouldBeGreaterThanOrEqual(1)
                        retrievedNotificationRule.name shouldBe "name"
                        retrievedNotificationRule.description shouldBe "description"
                    }
        }
    }
}
