package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestRecipientSmsDto
import de.unia.se.teamcq.TestUtils.getTestRecipientSmsEntity
import de.unia.se.teamcq.TestUtils.getTestRecipientSmsModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class RecipientSmsMapperTest : StringSpec() {

    private var recipientSmsMapper = Mappers.getMapper(IRecipientSmsMapper::class.java)

    init {

        "Convert model to entity" {

            val recipientSmsModel = getTestRecipientSmsModel()

            val recipientSmsEntity = recipientSmsMapper.modelToEntity(recipientSmsModel)

            recipientSmsEntity shouldBe getTestRecipientSmsEntity()
        }

        "Convert entity to model" {

            val recipientSmsEntity = getTestRecipientSmsEntity()

            val recipientSmsModel = recipientSmsMapper.entityToModel(recipientSmsEntity)

            recipientSmsModel shouldBe getTestRecipientSmsModel()
        }

        "Convert model to dto" {

            val recipientSmsModel = getTestRecipientSmsModel()

            val recipientSmsDto = recipientSmsMapper.modelToDto(recipientSmsModel)

            recipientSmsDto shouldBe getTestRecipientSmsDto()
        }

        "Convert dto to model" {

            val recipientSmsDto = getTestRecipientSmsDto()

            val recipientSmsModel = recipientSmsMapper.dtoToModel(recipientSmsDto)

            recipientSmsModel shouldBe getTestRecipientSmsModel()
        }
    }
}
