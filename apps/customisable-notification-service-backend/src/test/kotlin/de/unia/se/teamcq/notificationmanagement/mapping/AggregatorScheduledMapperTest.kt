package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledModel
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AggregatorScheduledMapperTest : StringSpec() {

    @Autowired
    lateinit var aggregatorScheduledMapper: AbstractAggregatorScheduledMapperImpl

    init {

        "Convert model to entity" {

            val aggregatorScheduledModel = getTestAggregatorScheduledModel()

            val aggregatorScheduledEntity = aggregatorScheduledMapper.modelToEntity(aggregatorScheduledModel)

            aggregatorScheduledEntity shouldBe getTestAggregatorScheduledEntity()
        }

        "Convert entity to model" {

            val aggregatorScheduledEntity = getTestAggregatorScheduledEntity()

            val aggregatorScheduledModel = aggregatorScheduledMapper.entityToModel(aggregatorScheduledEntity)

            val test = getTestAggregatorScheduledModel()

            aggregatorScheduledModel shouldBe test
        }

        "Convert model to dto" {

            val aggregatorScheduledModel = getTestAggregatorScheduledModel()

            val aggregatorScheduledDto = aggregatorScheduledMapper.modelToDto(aggregatorScheduledModel)

            aggregatorScheduledDto shouldBe getTestAggregatorScheduledDto()
        }

        "Convert dto to model" should {

            "Work if NotificationCronTrigger is legal" {

                val aggregatorScheduledDto = getTestAggregatorScheduledDto()

                val aggregatorScheduledModel = aggregatorScheduledMapper.dtoToModel(aggregatorScheduledDto)

                aggregatorScheduledModel shouldBe getTestAggregatorScheduledModel()
            }

            "Throw an exception if NotificationCronTrigger is illegal" {

                val aggregatorScheduledDto = getTestAggregatorScheduledDto().apply { notificationCronTrigger = "test" }

                shouldThrow<IllegalArgumentException> {
                    aggregatorScheduledMapper.dtoToModel(aggregatorScheduledDto)
                }
            }
        }
    }
}
