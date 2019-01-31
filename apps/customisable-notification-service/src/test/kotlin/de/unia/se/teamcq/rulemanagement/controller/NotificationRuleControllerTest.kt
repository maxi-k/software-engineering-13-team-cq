package de.unia.se.teamcq.rulemanagement.controller

import com.fasterxml.jackson.databind.ObjectMapper
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleDto
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestUserDto
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.service.IUserService
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.hamcrest.Matchers.hasSize
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationRuleControllerTest : StringSpec() {

    @MockK
    private lateinit var mockNotificationRuleMapper: INotificationRuleMapper

    @MockK
    private lateinit var notificationRuleService: INotificationRuleService

    @MockK
    private lateinit var userService: IUserService

    @InjectMockKs
    private lateinit var notificationRuleController: NotificationRuleController

    @MockK
    private lateinit var authentication: Authentication

    @MockK
    private lateinit var securityContext: SecurityContext

    init {
        MockKAnnotations.init(this)

        every { notificationRuleService.createNotificationRule(any(), any()) } returns getTestNotificationRuleModel().copy(56)

        every { notificationRuleService.getNotificationRule(getTestNotificationRuleModel().ruleId!!) } returns getTestNotificationRuleModel().copy(56)
        every { notificationRuleService.getNotificationRule(not(getTestNotificationRuleModel().ruleId!!)) } returns null

        every { notificationRuleService.updateNotificationRule(any()) } answers { firstArg() }
        every { notificationRuleService.deleteNotificationRule(any()) } just Runs

        every { mockNotificationRuleMapper.dtoToModel(any()) } returns getTestNotificationRuleModel().copy(56)
        every { mockNotificationRuleMapper.modelToDto(any()) } returns getTestNotificationRuleDto().copy(ruleId = 56)

        val mockMvc = MockMvcBuilders
                .standaloneSetup(notificationRuleController)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()

        val session = MockHttpSession()
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext)

        "GetNotificationRules should work" {

            every { notificationRuleService.getNotificationRulesForUser(any()) } returns
                    listOf(getTestNotificationRuleModel().copy(name = "ruleA"), getTestNotificationRuleModel().copy(name = "ruleB"))

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToDefault()

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule")
                    .session(session))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$", hasSize<Int>(2)))

            verify(exactly = 1) {
                notificationRuleService.getNotificationRulesForUser(any())
            }
        }

        "GetNotificationRule" should {

            "Return stored NotificationRules" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                mockMvc.perform(MockMvcRequestBuilders
                        .get("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                        .session(session))
                        .andExpect(status().isOk)
                        .andExpect { result ->
                            val returnedNotificationRuleDto = ObjectMapper()
                                    .readValue(result.response.contentAsString, NotificationRuleDto::class.java)

                            returnedNotificationRuleDto shouldBe getTestNotificationRuleDto().copy(ruleId = 56)
                        }

                verify(exactly = 1) {
                    notificationRuleService.getNotificationRule(any())
                }
            }

            "Returns a 404 error if there is no such NotificationRule" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                mockMvc.perform(MockMvcRequestBuilders
                        .get("/notification-rule-management/notification-rule/1045"))
                        .andExpect(status().isNotFound)
            }
        }

        "CreateNotificationRule NotificationRules" should {

            "Create NotificationRule if all arguments are legal" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                mockMvc.perform(MockMvcRequestBuilders
                        .post("/notification-rule-management/notification-rule")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(getTestNotificationRuleDto())))
                        .andExpect(status().isOk)
                        .andExpect { result ->
                            val returnedNotificationRuleDto = ObjectMapper()
                                    .readValue(result.response.contentAsString, NotificationRuleDto::class.java)

                            returnedNotificationRuleDto shouldBe getTestNotificationRuleDto().copy(ruleId = 56)
                        }

                verify(exactly = 1) {
                    notificationRuleService.createNotificationRule("Max Mustermann", any())
                }
            }

            "Not create NotificationRule if some arguments are illegal" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                val notificationRuleToCreate = getTestNotificationRuleDto().apply {
                    aggregator = getTestAggregatorCountingDto().apply {
                        notificationCountThreshold = -1
                    }
                }

                every { mockNotificationRuleMapper.dtoToModel(notificationRuleToCreate) } throws IllegalArgumentException()

                mockMvc.perform(MockMvcRequestBuilders
                        .post("/notification-rule-management/notification-rule")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(notificationRuleToCreate)))
                        .andExpect(status().isBadRequest)
            }
        }

        "Update NotificationRules" should {
            "Update if current user is the owner" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                mockMvc.perform(MockMvcRequestBuilders
                        .put("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(getTestNotificationRuleDto())))
                        .andExpect(status().isOk)
                        .andExpect { result ->
                            val returnedNotificationRuleDto = ObjectMapper()
                                    .readValue(result.response.contentAsString, NotificationRuleDto::class.java)

                            returnedNotificationRuleDto shouldBe getTestNotificationRuleDto().copy(ruleId = 56)
                        }

                verify(exactly = 1) {
                    notificationRuleService.updateNotificationRule(any())
                }
            }

            "Not update if current user isn't the owner" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToOtherValue()

                val notificationRuleUpate = getTestNotificationRuleDto().copy(owner = getTestUserDto().copy(name = "other user"))

                mockMvc.perform(MockMvcRequestBuilders
                        .put("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(notificationRuleUpate)))
                        .andExpect(status().isBadRequest)
            }

            "Not update if the user that was set isn't the current user" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                val notificationRuleUpate = getTestNotificationRuleDto().copy(owner = getTestUserDto().copy(name = "other user"))

                mockMvc.perform(MockMvcRequestBuilders
                        .put("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(notificationRuleUpate)))
                        .andExpect(status().isBadRequest)
            }

            "Not update if a aggregator parameter is invalid" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                val notificationRuleUpate = getTestNotificationRuleDto().apply {
                    aggregator = getTestAggregatorCountingDto().apply {
                        notificationCountThreshold = -1
                    }
                }

                every { mockNotificationRuleMapper.dtoToModel(notificationRuleUpate) } throws IllegalArgumentException()

                mockMvc.perform(MockMvcRequestBuilders
                        .put("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(notificationRuleUpate)))
                        .andExpect(status().isBadRequest)
            }
        }

        "Delete NotificationRules" should {
            "Delete if current user is the owner" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToDefault()

                mockMvc.perform(MockMvcRequestBuilders
                        .delete("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}"))
                        .andExpect(status().isOk)

                verify(exactly = 1) {
                    notificationRuleService.deleteNotificationRule(any())
                }
            }

            "Not delete if current user isn't the owner" {

                SecurityContextHolder.setContext(securityContext)
                setCurrentUserToOtherValue()

                mockMvc.perform(MockMvcRequestBuilders
                        .delete("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}"))
                        .andExpect(status().isBadRequest)
            }
        }
    }

    private fun setCurrentUserToDefault() {
        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "Max Mustermann"
        every { userService.getOrCreateUser(any()) } returns User("Max Mustermann", null, null, null)
    }

    private fun setCurrentUserToOtherValue() {
        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "other user"
        every { userService.getOrCreateUser(any()) } returns User("other user", null, null, null)
    }
}
