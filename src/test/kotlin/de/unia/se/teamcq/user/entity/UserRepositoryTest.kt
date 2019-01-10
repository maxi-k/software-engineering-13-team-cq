package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.TestUtils
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
    lateinit var userEnityRepository: IUserEntityRepository

    @Autowired
    lateinit var userRepository: UserRepository

    init {
        MockKAnnotations.init(this)

        val userEntity = getTestUserEntity()
        val userModel = getTestUserModel()

        "createOrSaveUser should work" {

            val savedUser = userRepository.createOrSaveUser(userModel)

            savedUser shouldBe userModel
        }

        "getOrCreateUser should work" {

            userEnityRepository.save(userEntity)

            val actualUser = userRepository.getOrCreateUser(userEntity.name!!)

            actualUser shouldBe TestUtils.getTestUserModel()
        }
    }
}
