package de.unia.se.teamcq.rulemanagement.controller

import com.google.gson.Gson
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleDto
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.mapping.INotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.service.IUserService
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
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

        val mockedNotificationRule = getTestNotificationRuleModel()
        val mockedNotificationRuleDto = getTestNotificationRuleDto()

        every { notificationRuleService.createNotificationRule(any(), any()) } returns mockedNotificationRule.copy(56)

        every { notificationRuleService.getNotificationRule(mockedNotificationRule.id!!) } returns mockedNotificationRule.copy(56)
        every { notificationRuleService.getNotificationRule(not(mockedNotificationRule.id!!)) } returns null

        every { mockNotificationRuleMapper.dtoToModel(any()) } returns mockedNotificationRule.copy(56)
        every { mockNotificationRuleMapper.modelToDto(any()) } returns mockedNotificationRuleDto.copy(id = 56)

        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "Max Mustermann"
        every { userService.getOrCreateUser(any()) } returns User("Max Mustermann", null, null, null)

        val mockMvc = MockMvcBuilders
                .standaloneSetup(notificationRuleController)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()

        val session = MockHttpSession()
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext)

        "returns stored notification rules" {

            SecurityContextHolder.setContext(securityContext)

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/${mockedNotificationRule.id}")
                    .session(session)
                    .header("Authorization", "Bearer test")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRuleDto = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRuleDto::class.java)

                        returnedNotificationRuleDto shouldBe mockedNotificationRuleDto.copy(id = 56)
                    }

            verify(exactly = 1) {
                notificationRuleService.getNotificationRule(any())
            }
        }

        "returns a 404 error if there is no such notification rules" {

            SecurityContextHolder.setContext(securityContext)

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/1045")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNotFound)
        }

        "inserts notification rules successfully" {

            SecurityContextHolder.setContext(securityContext)

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(mockedNotificationRule)))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRuleDto = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRuleDto::class.java)

                        returnedNotificationRuleDto shouldBe mockedNotificationRuleDto.copy(id = 56)
                    }

            verify(exactly = 1) {
                notificationRuleService.createNotificationRule("Max Mustermann", any())
            }
        }
    }
}
