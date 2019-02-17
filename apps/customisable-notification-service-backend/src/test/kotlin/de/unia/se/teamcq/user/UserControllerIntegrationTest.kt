package de.unia.se.teamcq.user

import com.fasterxml.jackson.databind.ObjectMapper
import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.getTestUserDto
import de.unia.se.teamcq.security.JwtConfig
import de.unia.se.teamcq.security.JwtTokenAuthenticationFilter
import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.dto.UserSettingsDto
import de.unia.se.teamcq.user.model.UserLocale
import de.unia.se.teamcq.user.model.UserNotificationType
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest : StringSpec() {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var jwtTokenAuthenticationFilter: JwtTokenAuthenticationFilter

    @Autowired
    lateinit var jwtConfig: JwtConfig

    val possibleRequestPaths = listOf(
            "/user-notification-settings",
            "/user-notification-settings/"
    )

    init {
        "Fail without Authorization" {

            val mockMvc = TestUtils.buildMockMvc(webApplicationContext)

            possibleRequestPaths.map { requestPath ->

                mockMvc.perform(MockMvcRequestBuilders
                        .get(requestPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            }
        }

        "Create users the first time they authenticate" {

            val mockMvc = TestUtils.buildMockMvc(webApplicationContext)
            val accessToken = jwtTokenAuthenticationFilter.createToken("New user")

            val possibleRequestPaths = listOf(
                    "/user-notification-settings",
                    "/user-notification-settings/"
            )

            possibleRequestPaths.map { requestPath ->
                mockMvc.perform(MockMvcRequestBuilders
                        .get(requestPath)
                        .headers(TestUtils.prepareAccessTokenHeader(jwtConfig, accessToken)))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect { result ->

                            val returnedUserDto = ObjectMapper()
                                    .readValue(result.response.contentAsString, UserDto::class.java)

                            val expectedCreatedUser = UserDto(
                                    name = "New user",
                                    userSettings = UserSettingsDto(
                                            returnedUserDto.userSettings!!.settingsId,
                                            UserNotificationType.EMAIL,
                                            UserLocale.EN
                                    )
                            )

                            returnedUserDto shouldBe expectedCreatedUser
                        }
            }
        }

        "Set user successfully" {

            val mockMvc = TestUtils.buildMockMvc(webApplicationContext)
            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val possibleRequestPaths = listOf(
                    "/user-notification-settings",
                    "/user-notification-settings/"
            )

            possibleRequestPaths.map { requestPath ->
                mockMvc.perform(MockMvcRequestBuilders
                        .put(requestPath)
                        .headers(TestUtils.prepareAccessTokenHeader(jwtConfig, accessToken))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(TestUtils.getTestUserDto())))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect { result ->

                            val returnedUserDto = ObjectMapper()
                                    .readValue(result.response.contentAsString, UserDto::class.java)

                            val expectedUserDto = getTestUserDto().apply {
                                userSettings!!.settingsId = returnedUserDto.userSettings!!.settingsId
                            }

                            returnedUserDto shouldBe expectedUserDto
                        }
            }
        }

        "Returns stored user" {

            val mockMvc = TestUtils.buildMockMvc(webApplicationContext)
            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val possibleRequestPaths = listOf(
                    "/user-notification-settings",
                    "/user-notification-settings/"
            )

            possibleRequestPaths.map { requestPath ->
                mockMvc.perform(MockMvcRequestBuilders
                        .put(requestPath)
                        .headers(TestUtils.prepareAccessTokenHeader(jwtConfig, accessToken))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(TestUtils.getTestUserDto())))
            }

            possibleRequestPaths.map { requestPath ->
                mockMvc.perform(MockMvcRequestBuilders
                        .get(requestPath)
                        .headers(TestUtils.prepareAccessTokenHeader(jwtConfig, accessToken)))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect { result ->

                            val returnedUserDto = ObjectMapper()
                                    .readValue(result.response.contentAsString, UserDto::class.java)

                            val expectedUserDto = getTestUserDto().apply {
                                userSettings!!.settingsId = returnedUserDto.userSettings!!.settingsId
                            }

                            returnedUserDto shouldBe expectedUserDto
                        }
            }
        }
    }
}
