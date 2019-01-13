package de.unia.se.teamcq.user.controller

import com.google.common.collect.ImmutableList
import com.google.gson.Gson
import de.unia.se.teamcq.TestUtils.getTestUserDto
import de.unia.se.teamcq.TestUtils.getTestUserModel
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

        every { userService.getOrCreateUser(any()) } returns getTestUserModel()

        every { userService.createOrSaveUser(any()) } returns getTestUserModel()

        every { mockUserMapper.dtoToModel(any()) } returns getTestUserModel()
        every { mockUserMapper.modelToDto(any()) } returns getTestUserDto()

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

        "Returns stored user" {

            SecurityContextHolder.setContext(securityContext)

            val possibleRequestPaths = listOf(
                    "/user-notification-settings",
                    "/user-notification-settings/"
            )

            possibleRequestPaths.map { requestPath ->
                mockMvc.perform(MockMvcRequestBuilders
                        .get(requestPath)
                        .session(session))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect { result ->
                            val returnedUserDto = gson.fromJson(
                                    result.response.contentAsString,
                                    UserDto::class.java)

                            returnedUserDto shouldBe getTestUserDto()
                        }
            }

            verify(exactly = possibleRequestPaths.size) {
                userService.getOrCreateUser(any())
            }
        }

        "Set user successfully" {

            SecurityContextHolder.setContext(securityContext)

            val possibleRequestPaths = listOf(
                    "/user-notification-settings",
                    "/user-notification-settings/"
            )

            possibleRequestPaths.map { requestPath ->
                mockMvc.perform(MockMvcRequestBuilders
                        .put(requestPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(gson.toJson(getTestUserDto())))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect { result ->
                            val returnedUser = gson.fromJson(
                                    result.response.contentAsString,
                                    UserDto::class.java)

                            returnedUser shouldBe getTestUserDto()
                        }
            }

            verify(exactly = possibleRequestPaths.size) {
                userService.createOrSaveUser(any())
            }
        }
    }
}
