package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingModel
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class AggregatorCountingMapperTest : StringSpec() {

    private var aggregatorCountingMapper = Mappers.getMapper(AbstractAggregatorCountingMapper::class.java)

    init {

        "Convert model to entity" {

            val aggregatorCountingModel = getTestAggregatorCountingModel()

            val aggregatorCountingEntity = aggregatorCountingMapper.modelToEntity(aggregatorCountingModel)

            aggregatorCountingEntity shouldBe getTestAggregatorCountingEntity()
        }

        "Convert entity to model" {

            val aggregatorCountingEntity = getTestAggregatorCountingEntity()

            val aggregatorCountingModel = aggregatorCountingMapper.entityToModel(aggregatorCountingEntity)

            aggregatorCountingModel shouldBe getTestAggregatorCountingModel()
        }

        "Convert model to dto" {

            val aggregatorCountingModel = getTestAggregatorCountingModel()

            val aggregatorCountingDto = aggregatorCountingMapper.modelToDto(aggregatorCountingModel)

            aggregatorCountingDto shouldBe getTestAggregatorCountingDto()
        }

        "Convert dto to model" should {

            "Work if CountingThreshold is legal" {

                val aggregatorCountingDto = getTestAggregatorCountingDto()

                val aggregatorCountingModel = aggregatorCountingMapper.dtoToModel(aggregatorCountingDto)

                aggregatorCountingModel shouldBe getTestAggregatorCountingModel()
            }

            "Throw an exception if CountingThreshold is illegal" {

                val aggregatorCountingDto = getTestAggregatorCountingDto().apply { notificationCountThreshold = -1 }

                shouldThrow<IllegalArgumentException> {
                    aggregatorCountingMapper.dtoToModel(aggregatorCountingDto)
                }
            }
        }
    }
}
