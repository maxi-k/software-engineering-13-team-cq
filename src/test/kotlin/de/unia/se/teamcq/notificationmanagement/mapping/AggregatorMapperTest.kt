package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getAggregatorImmediateModel
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingModel
import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediateDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediateEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledModel
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class AggregatorMapperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var aggregatorScheduledMapper: AbstractAggregatorScheduledMapper

    @MockK(relaxed = true)
    lateinit var AbstractAggregatorCountingdMapper: AbstractAggregatorCountingMapper

    @MockK(relaxed = true)
    lateinit var aggregatorImmediatelyMapper: IAggregatorImmediateMapper

    @InjectMockKs
    lateinit var aggregatorMapper: AggregatorMapper

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            aggregatorMapper.modelToEntity(getTestAggregatorScheduledModel())
            verify(exactly = 1) {
                aggregatorScheduledMapper.modelToEntity(any())
            }

            aggregatorMapper.modelToEntity(getTestAggregatorCountingModel())
            verify(exactly = 1) {
                AbstractAggregatorCountingdMapper.modelToEntity(any())
            }

            aggregatorMapper.modelToEntity(getAggregatorImmediateModel())
            verify(exactly = 1) {
                aggregatorImmediatelyMapper.modelToEntity(any())
            }
        }

        "Convert entity to model" {

            aggregatorMapper.entityToModel(getTestAggregatorScheduledEntity())
            verify(exactly = 1) {
                aggregatorScheduledMapper.entityToModel(any())
            }

            aggregatorMapper.entityToModel(getTestAggregatorCountingEntity())
            verify(exactly = 1) {
                AbstractAggregatorCountingdMapper.entityToModel(any())
            }

            aggregatorMapper.entityToModel(getTestAggregatorImmediateEntity())
            verify(exactly = 1) {
                aggregatorImmediatelyMapper.entityToModel(any())
            }
        }

        "Convert model to dto" {

            aggregatorMapper.modelToDto(getTestAggregatorScheduledModel())
            verify(exactly = 1) {
                aggregatorScheduledMapper.modelToDto(any())
            }

            aggregatorMapper.modelToDto(getTestAggregatorCountingModel())
            verify(exactly = 1) {
                AbstractAggregatorCountingdMapper.modelToDto(any())
            }

            aggregatorMapper.modelToDto(getAggregatorImmediateModel())
            verify(exactly = 1) {
                aggregatorImmediatelyMapper.modelToDto(any())
            }
        }

        "Convert dto to model" {

            aggregatorMapper.dtoToModel(getTestAggregatorScheduledDto())
            verify(exactly = 1) {
                aggregatorScheduledMapper.dtoToModel(any())
            }

            aggregatorMapper.dtoToModel(getTestAggregatorCountingDto())
            verify(exactly = 1) {
                AbstractAggregatorCountingdMapper.dtoToModel(any())
            }

            aggregatorMapper.dtoToModel(getTestAggregatorImmediateDto())
            verify(exactly = 1) {
                aggregatorImmediatelyMapper.dtoToModel(any())
            }
        }
    }
}
