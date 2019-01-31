package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestRecipientMailDto
import de.unia.se.teamcq.TestUtils.getTestRecipientMailEntity
import de.unia.se.teamcq.TestUtils.getTestRecipientMailModel
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class RecipientMailMapperTest : StringSpec() {

    private var recipientMailMapper = Mappers.getMapper(AbstractRecipientMailMapper::class.java)

    init {

        "Convert model to entity" {

            val recipientMailModel = getTestRecipientMailModel()

            val recipientMailEntity = recipientMailMapper.modelToEntity(recipientMailModel)

            recipientMailEntity shouldBe getTestRecipientMailEntity()
        }

        "Convert entity to model" {

            val recipientMailEntity = getTestRecipientMailEntity()

            val recipientMailModel = recipientMailMapper.entityToModel(recipientMailEntity)

            recipientMailModel shouldBe getTestRecipientMailModel()
        }

        "Convert model to dto" {

            val recipientMailModel = getTestRecipientMailModel()

            val recipientMailDto = recipientMailMapper.modelToDto(recipientMailModel)

            recipientMailDto shouldBe getTestRecipientMailDto()
        }

        "Convert dto to model" should {

            "Work if CountingThreshold is legal" {

                val recipientMailModel = getTestRecipientMailModel()

                val recipientMailDto = recipientMailMapper.modelToDto(recipientMailModel)

                recipientMailDto shouldBe getTestRecipientMailDto()
            }

            "Throw an exception if CountingThreshold is illegal" {

                val recipientMailDto = getTestRecipientMailDto().apply { mailAddress = "@@@xyz" }

                shouldThrow<IllegalArgumentException> {
                    recipientMailMapper.dtoToModel(recipientMailDto)
                }
            }
        }
    }
}
