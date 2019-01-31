package de.unia.se.teamcq.user.mapping

import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class UserMapperTest : StringSpec() {

    private var userMapper: IUserMapper = Mappers.getMapper(IUserMapper::class.java)

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
    }
}
