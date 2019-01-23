package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestRecipientDtos
import de.unia.se.teamcq.TestUtils.getTestRecipientEntities
import de.unia.se.teamcq.TestUtils.getTestRecipientModels
import de.unia.se.teamcq.notificationmanagement.mapping.IRecipientMapper
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class RecipientMapperHelperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var recipientMapper: IRecipientMapper

    @InjectMockKs
    lateinit var recipientMapperHelper: RecipientMapperHelper

    init {
        MockKAnnotations.init(this)

        "Convert model to dto" {

            val recipientModels = getTestRecipientModels()

            recipientMapperHelper.modelToDto(recipientModels).size shouldBe 2

            verify(exactly = 2) {
                recipientMapper.modelToDto(any())
            }
        }

        "Convert dto to model" {

            val recipientDtos = getTestRecipientDtos()

            recipientMapperHelper.dtoToModel(recipientDtos).size shouldBe 2

            verify(exactly = 2) {
                recipientMapper.dtoToModel(any())
            }
        }

        "Convert model to entity" {

            val recipientModels = getTestRecipientModels()

            recipientMapperHelper.modelToEntity(recipientModels).size shouldBe 2

            verify(exactly = 2) {
                recipientMapper.modelToEntity(any())
            }
        }

        "Convert entity to model" {

            val recipientEntities = getTestRecipientEntities()

            recipientMapperHelper.entityToModel(recipientEntities).size shouldBe 2

            verify(exactly = 2) {
                recipientMapper.entityToModel(any())
            }
        }
    }
}
