package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class AggregatorCountingMapperTest : StringSpec() {

    private var notificationAggregatorCountingMapper = Mappers.getMapper(INotificationAggregatorCountingMapper::class.java)

    init {

        "Convert model to entity" {

            val notificationAggregatorCountingModel = getTestAggregatorCountingModel()

            val notificationAggregatorCountingEntity = notificationAggregatorCountingMapper.modelToEntity(notificationAggregatorCountingModel)

            notificationAggregatorCountingEntity shouldBe getTestAggregatorCountingEntity()
        }

        "Convert entity to model" {

            val notificationAggregatorCountingEntity = getTestAggregatorCountingEntity()

            val notificationAggregatorCountingModel = notificationAggregatorCountingMapper.entityToModel(notificationAggregatorCountingEntity)

            notificationAggregatorCountingModel shouldBe getTestAggregatorCountingModel()
        }

        "Convert model to dto" {

            val notificationAggregatorCountingModel = getTestAggregatorCountingModel()

            val notificationAggregatorCountingDto = notificationAggregatorCountingMapper.modelToDto(notificationAggregatorCountingModel)

            notificationAggregatorCountingDto shouldBe getTestAggregatorCountingDto()
        }

        "Convert dto to model" {

            val notificationAggregatorCountingDto = getTestAggregatorCountingDto()

            val notificationAggregatorCountingModel = notificationAggregatorCountingMapper.dtoToModel(notificationAggregatorCountingDto)

            notificationAggregatorCountingModel shouldBe getTestAggregatorCountingModel()
        }
    }
}
