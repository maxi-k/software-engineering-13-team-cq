package de.unia.se.teamcq.user.mapping

import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class UserMapperTest : StringSpec() {

    private var vehicleStateMapper: IUserMapper = Mappers.getMapper(IUserMapper::class.java)

    init {

        "convert model to entity" {

            val user = getTestUserModel()

            val userEntity = vehicleStateMapper.modelToEntity(user)

            userEntity shouldNotBe null
            userEntity.cellPhoneNumber shouldBe user.cellPhoneNumber
            userEntity.mailAddress shouldBe user.mailAddress
            userEntity.name shouldBe user.name
            userEntity.userSettings!!.userNotificationType shouldBe user.userSettings!!.userNotificationType
        }

        "convert entity to model" {

            val userEntity = getTestUserEntity()

            val user = vehicleStateMapper.entityToModel(userEntity)

            user shouldNotBe null
            user.cellPhoneNumber shouldBe userEntity.cellPhoneNumber
            user.mailAddress shouldBe userEntity.mailAddress
            user.name shouldBe userEntity.name
            user.userSettings!!.userNotificationType shouldBe userEntity.userSettings!!.userNotificationType
        }
    }
}
