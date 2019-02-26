package de.unia.se.teamcq.user.mapping

import de.unia.se.teamcq.TestUtils.getTestUserDto
import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class UserMapperTest : StringSpec() {

    private var userMapper: AbstractUserMapper = Mappers.getMapper(AbstractUserMapper::class.java)

    init {

        "Convert model to entity" {

            val userModel = getTestUserModel()

            val userEntity = userMapper.modelToEntity(userModel)

            userEntity shouldBe getTestUserEntity()
        }

        "Convert entity to model" {

            val userEntity = getTestUserEntity()

            val userModel = userMapper.entityToModel(userEntity)

            userModel shouldBe getTestUserModel()
        }

        "Convert model to dto" {

            val userModel = getTestUserModel()

            val userDto = userMapper.modelToDto(userModel)

            userDto shouldBe getTestUserDto()
        }

        "Convert dto to model" should {

            "Work for legal arguments" {

                val userDto = getTestUserDto()

                userMapper.checkLegalArguments(userDto)
                val userModel = userMapper.dtoToModel(userDto)

                userModel shouldBe getTestUserModel()
            }

            "Throw an Exception if userSettings is null" {

                shouldThrow<IllegalArgumentException> {

                    val userDto = getTestUserDto().apply {
                        this.userSettings = null
                    }
                    userMapper.checkLegalArguments(userDto)

                    userMapper.dtoToModel(userDto)
                }
            }

            "Throw an Exception if userSettings.locale is null" {

                shouldThrow<IllegalArgumentException> {

                    val userDto = getTestUserDto().apply {
                        this.userSettings!!.locale = null
                    }
                    userMapper.checkLegalArguments(userDto)

                    userMapper.dtoToModel(userDto)
                }
            }

            "Throw an Exception if userSettings.userNotificationType is null" {

                shouldThrow<IllegalArgumentException> {

                    val userDto = getTestUserDto().apply {
                        this.userSettings!!.userNotificationType = null
                    }
                    userMapper.checkLegalArguments(userDto)

                    userMapper.dtoToModel(userDto)
                }
            }
        }
    }
}
