package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestRecipientMailDto
import de.unia.se.teamcq.TestUtils.getTestRecipientMailEntity
import de.unia.se.teamcq.TestUtils.getTestRecipientMailModel
import de.unia.se.teamcq.TestUtils.getTestRecipientSmsDto
import de.unia.se.teamcq.TestUtils.getTestRecipientSmsEntity
import de.unia.se.teamcq.TestUtils.getTestRecipientSmsModel
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class RecipientMapperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var recipientMailMapper: AbstractRecipientMailMapper

    @MockK(relaxed = true)
    lateinit var recipientSmsMapper: IRecipientSmsMapper

    @InjectMockKs
    lateinit var recipientMapper: RecipientMapper

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            recipientMapper.modelToEntity(getTestRecipientMailModel())
            verify(exactly = 1) {
                recipientMailMapper.modelToEntity(any())
            }

            recipientMapper.modelToEntity(getTestRecipientSmsModel())
            verify(exactly = 1) {
                recipientSmsMapper.modelToEntity(any())
            }
        }

        "Convert entity to model" {

            recipientMapper.entityToModel(getTestRecipientMailEntity())
            verify(exactly = 1) {
                recipientMailMapper.entityToModel(any())
            }

            recipientMapper.entityToModel(getTestRecipientSmsEntity())
            verify(exactly = 1) {
                recipientSmsMapper.entityToModel(any())
            }
        }

        "Convert model to dto" {

            recipientMapper.modelToDto(getTestRecipientMailModel())
            verify(exactly = 1) {
                recipientMailMapper.modelToDto(any())
            }

            recipientMapper.modelToDto(getTestRecipientSmsModel())
            verify(exactly = 1) {
                recipientSmsMapper.modelToDto(any())
            }
        }

        "Convert dto to model" {

            recipientMapper.dtoToModel(getTestRecipientMailDto())
            verify(exactly = 1) {
                recipientMailMapper.dtoToModel(any())
            }

            recipientMapper.dtoToModel(getTestRecipientSmsDto())
            verify(exactly = 1) {
                recipientSmsMapper.dtoToModel(any())
            }
        }
    }
}
