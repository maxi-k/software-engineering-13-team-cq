package de.unia.se.teamcq.rulemanagement.controller

import com.google.gson.Gson
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.mapping.NotificationRuleMapper
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.rulemanagement.service.NotificationRuleService
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
import java.util.Optional

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationRuleControllerTest : StringSpec() {

    @MockK
    private lateinit var mockNotificationRuleMapper: NotificationRuleMapper

    @MockK
    private lateinit var notificationRuleService: NotificationRuleService

    @InjectMockKs
    private lateinit var notificationRuleController: NotificationRuleController

    @MockK
    private lateinit var authentication: Authentication

    @MockK
    private lateinit var securityContext: SecurityContext

    private val gson = Gson()

    init {
        MockKAnnotations.init(this)

        // Define a vehicle status we are working with
        val mockedNotificationRule = NotificationRule(id = 42, name = "test", description = "test")
        val mockedNotificationRuleDto = NotificationRuleDto(id = 42, name = "test", description = "test")

        // Define what the mocked service should return
        // - 'create' should return just the passed object
        every { notificationRuleService.createNotificationRule(any(), any()) } returns Optional.of(mockedNotificationRule.copy(2))
        // - 'get' should return the only status we know, `mockedVehicleStatus`
        every { notificationRuleService.getNotificationRule(mockedNotificationRule.id!!) } returns Optional.of(mockedNotificationRule)
        every { notificationRuleService.getNotificationRule(not(mockedNotificationRule.id!!)) } returns Optional.empty()

        every { mockNotificationRuleMapper.dtoToModel(any()) } returns mockedNotificationRule
        every { mockNotificationRuleMapper.modelToDto(any()) } returns mockedNotificationRuleDto.copy(id = 2)

        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "username"

        val mockMvc = MockMvcBuilders
                .standaloneSetup(notificationRuleController)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()

        val session = MockHttpSession()
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext)

        "returns stored vehicle status" {

            SecurityContextHolder.setContext(securityContext)

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/${mockedNotificationRule.id}")
                    .session(session)
                    .header("Authorization", "Bearer test")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRule = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRule::class.java)

                        returnedNotificationRule shouldBe mockedNotificationRule.copy(id = 2)
                    }

            verify(exactly = 1) {
                notificationRuleService.getNotificationRule(any())
            }
        }

        "returns a 404 error if there is no such status" {

            SecurityContextHolder.setContext(securityContext)

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/notification-rule-management/notification-rule/1029")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNotFound)
        }

        "inserts vehicle status successfully" {

            SecurityContextHolder.setContext(securityContext)

            // Perform a POST request to /events/vehicle-status
            mockMvc.perform(MockMvcRequestBuilders
                    .post("/notification-rule-management/notification-rule")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(mockedNotificationRule)))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRule = gson.fromJson(
                                result.response.contentAsString,
                                NotificationRule::class.java)

                        returnedNotificationRule shouldBe mockedNotificationRule.copy(id = 2)
                    }

            // Verify that the mocked service was called exactly once
            verify(exactly = 1) {
                notificationRuleService.createNotificationRule("username", any())
            }
        }
    }
}
