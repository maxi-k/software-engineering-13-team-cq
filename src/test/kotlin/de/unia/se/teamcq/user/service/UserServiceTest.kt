package de.unia.se.teamcq.user.service

import de.unia.se.teamcq.TestUtils.getTestUserModel
import de.unia.se.teamcq.user.entity.IUserRepository
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class UserServiceTest : StringSpec() {

    @MockK
    private lateinit var userRepository: IUserRepository

    @InjectMockKs
    private lateinit var userService: UserService

    init {
        MockKAnnotations.init(this)

        val mockedUser = getTestUserModel()

        "getOrCreateUser should work" {
            every { userRepository.getOrCreateUser(any()) } returns mockedUser

            val user = userService.getOrCreateUser("test")
            user shouldBe mockedUser

            verify(exactly = 1) {
                userService.getOrCreateUser(any())
            }
        }

        "createOrSaveUser should work" {
            every { userRepository.createOrSaveUser(any()) } returns mockedUser

            val user = userService.createOrSaveUser(mockedUser)
            user shouldBe mockedUser

            verify(exactly = 1) {
                userService.createOrSaveUser(any())
            }
        }
    }
}
