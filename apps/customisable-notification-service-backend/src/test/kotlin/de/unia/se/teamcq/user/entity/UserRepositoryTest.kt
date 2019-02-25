package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserRepositoryTest : StringSpec() {

    @Autowired
    lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    lateinit var userRepository: UserRepository

    init {
        MockKAnnotations.init(this)

        "CreateOrSaveUser should work" {

            val savedUser = userRepository.createOrSaveUser(getTestUserModel())

            val expectedUser = getTestUserModel().apply {
                userSettings!!.settingsId = savedUser!!.userSettings!!.settingsId
            }

            savedUser shouldBe expectedUser
        }

        "GetOrCreateUser should work" {

            userEntityRepository.save(getTestUserEntity())

            val actualUser = userRepository.getOrCreateUser(getTestUserEntity().name!!)

            val expectedUser = getTestUserModel().apply {
                userSettings!!.settingsId = actualUser!!.userSettings!!.settingsId
            }

            actualUser shouldBe expectedUser
        }
    }
}
