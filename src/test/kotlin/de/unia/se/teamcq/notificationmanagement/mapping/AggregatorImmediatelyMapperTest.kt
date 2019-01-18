package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getAggregatorImmediateModel
import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediateDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediateEntity
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class AggregatorImmediatelyMapperTest : StringSpec() {

    private var aggregatorImmediatelyMapper = Mappers.getMapper(IAggregatorImmediateMapper::class.java)

    init {

        "Convert model to entity" {

            val aggregatorImmediatelyModel = getAggregatorImmediateModel()

            val aggregatorImmediatelyEntity = aggregatorImmediatelyMapper.modelToEntity(aggregatorImmediatelyModel)

            aggregatorImmediatelyEntity shouldBe getTestAggregatorImmediateEntity()
        }

        "Convert entity to model" {

            val aggregatorImmediateEntity = getTestAggregatorImmediateEntity()

            val aggregatorImmediateModel = aggregatorImmediatelyMapper.entityToModel(aggregatorImmediateEntity)

            aggregatorImmediateModel shouldBe getAggregatorImmediateModel()
        }

        "Convert model to dto" {

            val aggregatorImmediateModel = getAggregatorImmediateModel()

            val aggregatorImmediatelyDto = aggregatorImmediatelyMapper.modelToDto(aggregatorImmediateModel)

            aggregatorImmediatelyDto shouldBe getTestAggregatorImmediateDto()
        }

        "Convert dto to model" {

            val aggregatorImmediateDto = getTestAggregatorImmediateDto()

            val aggregatorImmediateModel = aggregatorImmediatelyMapper.dtoToModel(aggregatorImmediateDto)

            aggregatorImmediateModel shouldBe getAggregatorImmediateModel()
        }
    }
}
