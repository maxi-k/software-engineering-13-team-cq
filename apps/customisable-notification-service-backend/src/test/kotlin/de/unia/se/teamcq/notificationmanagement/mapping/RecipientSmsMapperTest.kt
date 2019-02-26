package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestRecipientSmsDto
import de.unia.se.teamcq.TestUtils.getTestRecipientSmsEntity
import de.unia.se.teamcq.TestUtils.getTestRecipientSmsModel
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class RecipientSmsMapperTest : StringSpec() {

    private var recipientSmsMapper = Mappers.getMapper(AbstractRecipientSmsMapper::class.java)

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

        "Convert dto to model" should {

            "Work if phoneNumber is legal" {

                val recipientSmsDto = getTestRecipientSmsDto()

                val recipientSmsModel = recipientSmsMapper.dtoToModel(recipientSmsDto)

                recipientSmsModel shouldBe getTestRecipientSmsModel()
            }

            "Throw an exception if phoneNumber is null or blank" {

                shouldThrow<IllegalArgumentException> {

                    val recipientSmsDto = getTestRecipientSmsDto().apply {
                        phoneNumber = null
                    }

                    recipientSmsMapper.dtoToModel(recipientSmsDto)
                }

                shouldThrow<IllegalArgumentException> {

                    val recipientSmsDto = getTestRecipientSmsDto().apply {
                        phoneNumber = ""
                    }

                    recipientSmsMapper.dtoToModel(recipientSmsDto)
                }
            }
        }
    }
}
