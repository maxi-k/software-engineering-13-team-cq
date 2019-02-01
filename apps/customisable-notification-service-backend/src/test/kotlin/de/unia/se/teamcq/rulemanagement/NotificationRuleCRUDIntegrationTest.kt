package de.unia.se.teamcq.rulemanagement

import com.fasterxml.jackson.databind.ObjectMapper
import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.buildMockMvc
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleDto
import de.unia.se.teamcq.TestUtils.prepareAccessTokenHeader
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.security.JwtConfig
import de.unia.se.teamcq.security.JwtTokenAuthenticationFilter
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.hamcrest.Matchers.hasSize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationRuleCRUDIntegrationTest : StringSpec() {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var jwtTokenAuthenticationFilter: JwtTokenAuthenticationFilter

    @Autowired
    lateinit var jwtConfig: JwtConfig

    init {
        "Fail without Authorization" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val notificationRuleToCreate = getTestNotificationRuleDto()

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(ObjectMapper().writeValueAsString(notificationRuleToCreate)))
                    .andExpect(status().isUnauthorized)
        }

        "Fail for invalid Authentication Token" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val notificationRuleToCreate = getTestNotificationRuleDto()

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .headers(prepareAccessTokenHeader(jwtConfig, "faketoken"))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(ObjectMapper().writeValueAsString(notificationRuleToCreate)))
                    .andExpect(status().isUnauthorized)
        }

        "Create and get a NotificationRule using a valid token" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val notificationRuleToCreate = getTestNotificationRuleDto().copy(ruleId = 2)

            val createdNotificationRule = postNotificationRule(mockMvc, notificationRuleToCreate, accessToken)

            val ruleId = createdNotificationRule.ruleId!!

            val retrievedNotificationRule = getNotificationRule(mockMvc, accessToken, ruleId)

            retrievedNotificationRule.ruleId!!.shouldBeGreaterThanOrEqual(1)
            retrievedNotificationRule.name shouldBe "rule_name"
            retrievedNotificationRule.description shouldBe "description"
        }

        "Create and get all NotificationRules for a user using a valid tokens" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val accessTokenA = jwtTokenAuthenticationFilter.createToken("UserA")

            val accessTokenB = jwtTokenAuthenticationFilter.createToken("UserB")

            val notificationRuleToCreateA = getTestNotificationRuleDto().copy(name = "NotificationRuleA")
            val notificationRuleToCreateB = getTestNotificationRuleDto().copy(name = "NotificationRuleB")
            val notificationRuleToCreateC = getTestNotificationRuleDto().copy(name = "NotificationRuleC")

            postNotificationRule(mockMvc, notificationRuleToCreateA, accessTokenA)
            postNotificationRule(mockMvc, notificationRuleToCreateB, accessTokenB)
            postNotificationRule(mockMvc, notificationRuleToCreateC, accessTokenA)

            val resultJsonString = mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/")
                    .headers(prepareAccessTokenHeader(jwtConfig, accessTokenA)))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$", hasSize<Any>(2)))
                    .andReturn()
                    .response
                    .contentAsString

            resultJsonString.shouldContain(notificationRuleToCreateA.name!!)
            resultJsonString.shouldContain(notificationRuleToCreateC.name!!)
        }

        "Create and delete a NotificationRule using a valid token" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val notificationRuleToCreate = getTestNotificationRuleDto()

            val createdNotificationRule = postNotificationRule(mockMvc, notificationRuleToCreate, accessToken)

            val ruleId = createdNotificationRule.ruleId!!

            mockMvc.perform(MockMvcRequestBuilders
                    .delete("/notification-rule-management/notification-rule/$ruleId")
                    .headers(prepareAccessTokenHeader(jwtConfig, accessToken)))
                    .andExpect(status().isOk)

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/$ruleId")
                    .headers(prepareAccessTokenHeader(jwtConfig, accessToken)))
                    .andExpect(status().isNotFound)
        }

        "Create and update a NotificationRule using a valid token" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val notificationRuleToCreate = getTestNotificationRuleDto()

            val createdNotificationRule = postNotificationRule(mockMvc, notificationRuleToCreate, accessToken)

            val ruleId = createdNotificationRule.ruleId!!

            val notificationRuleUpdate = createdNotificationRule.copy(name = "updated predicateFieldProviderName")

            mockMvc.perform(MockMvcRequestBuilders
                    .put("/notification-rule-management/notification-rule/$ruleId")
                    .headers(prepareAccessTokenHeader(jwtConfig, accessToken))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(ObjectMapper().writeValueAsString(notificationRuleUpdate)))
                    .andExpect(status().isOk)

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/$ruleId")
                    .headers(prepareAccessTokenHeader(jwtConfig, accessToken)))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRule = ObjectMapper()
                                .readValue(result.response.contentAsString, NotificationRuleDto::class.java)

                        returnedNotificationRule!! shouldBe notificationRuleUpdate
                    }
        }

        "CreateNotificationRule should fail if some arguments are illegal" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val notificationRuleToCreate = getTestNotificationRuleDto().apply {
                aggregator = TestUtils.getTestAggregatorCountingDto().apply {
                    notificationCountThreshold = -1
                }
            }

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .headers(prepareAccessTokenHeader(jwtConfig, accessToken))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(ObjectMapper().writeValueAsString(notificationRuleToCreate)))
                    .andExpect(status().isBadRequest)
        }

        "UpdateNotificationRule should fail if some arguments are illegal" {

            val mockMvc = buildMockMvc(webApplicationContext)

            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val notificationRuleToCreate = getTestNotificationRuleDto().apply {
                aggregator = TestUtils.getTestAggregatorCountingDto().apply {
                    ruleId = 2
                    notificationCountThreshold = -1
                }
            }

            mockMvc.perform(MockMvcRequestBuilders
                    .put("/notification-rule-management/notification-rule/${notificationRuleToCreate.ruleId}")
                    .headers(prepareAccessTokenHeader(jwtConfig, accessToken))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(ObjectMapper().writeValueAsString(notificationRuleToCreate)))
                    .andExpect(status().isBadRequest)
        }
    }

    fun postNotificationRule(
        mockMvc: MockMvc,
        notificationRuleToCreate: NotificationRuleDto,
        accessToken: String
    ): NotificationRuleDto {

        var returnedNotificationRule: NotificationRuleDto? = null

        mockMvc.perform(MockMvcRequestBuilders
                .post("/notification-rule-management/notification-rule")
                .headers(prepareAccessTokenHeader(jwtConfig, accessToken))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(notificationRuleToCreate)))
                .andExpect(status().isOk)
                .andExpect { result ->
                    returnedNotificationRule = ObjectMapper()
                            .readValue(result.response.contentAsString, NotificationRuleDto::class.java)

                    returnedNotificationRule!!.ruleId!!.shouldBeGreaterThanOrEqual(1)
                    returnedNotificationRule!!.name shouldBe notificationRuleToCreate.name
                    returnedNotificationRule!!.description shouldBe notificationRuleToCreate.description
                }

        return returnedNotificationRule!!
    }

    fun getNotificationRule(
        mockMvc: MockMvc,
        accessToken: String,
        ruleId: Long
    ): NotificationRuleDto {

        val result = mockMvc.perform(MockMvcRequestBuilders
                .get("/notification-rule-management/notification-rule/$ruleId")
                .headers(prepareAccessTokenHeader(jwtConfig, accessToken)))
                .andExpect(status().isOk)
                .andReturn()

        val retrievedNotificationRule = ObjectMapper()
                .readValue(result.response.contentAsString, NotificationRuleDto::class.java)

        return retrievedNotificationRule!!
    }
}
