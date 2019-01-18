package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediatelyDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediatelyEntity
import de.unia.se.teamcq.TestUtils.getAggregatorImmediatelyModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class AggregatorImmediatelyMapperTest : StringSpec() {

    private var notificationAggregatorImmediatelyMapper = Mappers.getMapper(INotificationAggregatorImmediatetlyMapper::class.java)

    init {

        // TODO: Clean up these tests. We can probably create a higher order util functions that does this

        "Convert model to entity" {

            val notificationAggregatorImmediatelyModel = getAggregatorImmediatelyModel()

            val notificationAggregatorImmediatelyEntity = notificationAggregatorImmediatelyMapper.modelToEntity(notificationAggregatorImmediatelyModel)

            notificationAggregatorImmediatelyEntity shouldBe getTestAggregatorImmediatelyEntity()
        }

        "Convert entity to model" {

            val notificationAggregatorImmediatelyEntity = getTestAggregatorImmediatelyEntity()

            val notificationAggregatorImmediatelyModel = notificationAggregatorImmediatelyMapper.entityToModel(notificationAggregatorImmediatelyEntity)

            notificationAggregatorImmediatelyModel shouldBe getAggregatorImmediatelyModel()
        }

        "Convert model to dto" {

            val notificationAggregatorImmediatlyModel = getAggregatorImmediatelyModel()

            val notificationAggregatorImmediatelyDto = notificationAggregatorImmediatelyMapper.modelToDto(notificationAggregatorImmediatlyModel)

            notificationAggregatorImmediatelyDto shouldBe getTestAggregatorImmediatelyDto()
        }

        "Convert dto to model" {

            val notificationAggregatorImmediatelyDto = getTestAggregatorImmediatelyDto()

            val notificationAggregatorImmediatelyModel = notificationAggregatorImmediatelyMapper.dtoToModel(notificationAggregatorImmediatelyDto)

            notificationAggregatorImmediatelyModel shouldBe getAggregatorImmediatelyModel()
        }
    }
}
