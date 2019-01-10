package de.unia.se.teamcq.user.controller

import com.google.gson.Gson
import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.mapping.IUserMapper
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ContextConfiguration(classes = [(TestConfiguration::class)])
class UserControllerTest : StringSpec() {

    @MockK
    private lateinit var mockUserMapper: IUserMapper

    @MockK
    private lateinit var userService: IUserService

    @InjectMockKs
    private lateinit var userController: UserController

    @MockK
    private lateinit var authentication: Authentication

    @MockK
    private lateinit var securityContext: SecurityContext

    private val gson = Gson()

    init {
        MockKAnnotations.init(this)

        val mockedUser = TestUtils.getTestUserModel()
        val mockedUserDto = TestUtils.getTestUserDto()

        every { userService.getOrCreateUser(any()) } returns mockedUser

        every { userService.createOrSaveUser(any()) } returns mockedUser

        every { mockUserMapper.dtoToModel(any()) } returns mockedUser
        every { mockUserMapper.modelToDto(any()) } returns mockedUserDto

        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "Max Mustermann"

        val mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()

        val session = MockHttpSession()
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext)

        "returns stored user" {

            SecurityContextHolder.setContext(securityContext)

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/user-notification-settings/")
                    .session(session)
                    .header("Authorization", "Bearer test")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect { result ->
                        val returnedUserDto = gson.fromJson(
                                result.response.contentAsString,
                                UserDto::class.java)

                        returnedUserDto shouldBe mockedUserDto
                    }

            verify(exactly = 1) {
                userService.getOrCreateUser(any())
            }
        }

        "set user successfully" {

            SecurityContextHolder.setContext(securityContext)

            mockMvc.perform(MockMvcRequestBuilders
                    .put("/user-notification-settings/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(mockedUserDto)))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect { result ->
                        val returnedNotificationRule = gson.fromJson(
                                result.response.contentAsString,
                                UserDto::class.java)

                        returnedNotificationRule shouldBe mockedUserDto
                    }

            verify(exactly = 1) {
                userService.createOrSaveUser(any())
            }
        }
    }
}
