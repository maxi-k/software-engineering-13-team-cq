package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AggregatorScheduledMapperTest : StringSpec() {

    @Autowired
    lateinit var aggregatorScheduledMapper: IAggregatorScheduledMapperImpl

    init {

        "Convert model to entity" {

            val aggregatorScheduledModel = getTestAggregatorScheduledModel()

            val aggregatorScheduledEntity = aggregatorScheduledMapper.modelToEntity(aggregatorScheduledModel)

            aggregatorScheduledEntity shouldBe getTestAggregatorScheduledEntity()
        }

        "Convert entity to model" {

            val aggregatorScheduledEntity = getTestAggregatorScheduledEntity()

            val aggregatorScheduledModel = aggregatorScheduledMapper.entityToModel(aggregatorScheduledEntity)

            aggregatorScheduledModel shouldBe getTestAggregatorScheduledModel()
        }

        "Convert model to dto" {

            val aggregatorScheduledModel = getTestAggregatorScheduledModel()

            val aggregatorScheduledDto = aggregatorScheduledMapper.modelToDto(aggregatorScheduledModel)

            aggregatorScheduledDto shouldBe getTestAggregatorScheduledDto()
        }

        "Convert dto to model" {

            val aggregatorScheduledDto = getTestAggregatorScheduledDto()

            val aggregatorScheduledModel = aggregatorScheduledMapper.dtoToModel(aggregatorScheduledDto)

            aggregatorScheduledModel shouldBe getTestAggregatorScheduledModel()
        }
    }
}
