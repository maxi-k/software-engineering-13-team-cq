package de.unia.se.teamcq.rulemanagement.controller

import com.google.gson.Gson
import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleDto
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestUserDto
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import de.unia.se.teamcq.security.JwtConfig
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.service.IUserService
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import io.mockk.Runs
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
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

    private val gson = Gson()

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

        "Returns stored NotificationRules" {

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToDefault()

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                    .session(session))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRuleDto = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRuleDto::class.java)

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

        "Insert NotificationRules successfully" {

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToDefault()

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(getTestNotificationRuleDto())))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRuleDto = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRuleDto::class.java)

                        returnedNotificationRuleDto shouldBe getTestNotificationRuleDto().copy(ruleId = 56)
                    }

            verify(exactly = 1) {
                notificationRuleService.createNotificationRule("Max Mustermann", any())
            }
        }

        "Update NotificationRules successfully if current user is the owner" {

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToDefault()

            mockMvc.perform(MockMvcRequestBuilders
                    .put("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(getTestNotificationRuleDto())))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRuleDto = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRuleDto::class.java)

                        returnedNotificationRuleDto shouldBe getTestNotificationRuleDto().copy(ruleId = 56)
                    }

            verify(exactly = 1) {
                notificationRuleService.updateNotificationRule(any())
            }
        }

        "Not update NotificationRules if current user isn't the owner" {

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToOtherValue()

            val notificationRuleUpate = getTestNotificationRuleDto().copy(owner = getTestUserDto().copy(name = "other user"))

            mockMvc.perform(MockMvcRequestBuilders
                    .put("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(notificationRuleUpate)))
                    .andExpect(status().isBadRequest)
        }

        "Not update NotificationRules if the user that was set isn't the current user" {

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToDefault()

            val notificationRuleUpate = getTestNotificationRuleDto().copy(owner = getTestUserDto().copy(name = "other user"))

            mockMvc.perform(MockMvcRequestBuilders
                    .put("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(notificationRuleUpate)))
                    .andExpect(status().isBadRequest)
        }

        "Delete NotificationRules successfully if current user is the owner" {

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToDefault()

            mockMvc.perform(MockMvcRequestBuilders
                    .delete("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}"))
                    .andExpect(status().isOk)

            verify(exactly = 1) {
                notificationRuleService.deleteNotificationRule(any())
            }
        }

        "Not delete NotificationRules if current user isn't the owner" {

            SecurityContextHolder.setContext(securityContext)
            setCurrentUserToOtherValue()

            mockMvc.perform(MockMvcRequestBuilders
                    .delete("/notification-rule-management/notification-rule/${getTestNotificationRuleDto().ruleId}"))
                    .andExpect(status().isBadRequest)
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
