package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorCountingEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorCountingModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationAggregatorCountingMapperTest : StringSpec() {

    private var notificationAggregatorCountingMapper = Mappers.getMapper(INotificationAggregatorCountingMapper::class.java)

    init {

        "Convert model to entity" {

            val notificationAggregatorCountingModel = getTestNotificationAggregatorCountingModel()

            val notificationAggregatorCountingEntity = notificationAggregatorCountingMapper.modelToEntity(notificationAggregatorCountingModel)

            notificationAggregatorCountingEntity shouldBe getTestNotificationAggregatorCountingEntity()
        }

        "Convert entity to model" {

            val notificationAggregatorCountingEntity = getTestNotificationAggregatorCountingEntity()

            val notificationAggregatorCountingModel = notificationAggregatorCountingMapper.entityToModel(notificationAggregatorCountingEntity)

            notificationAggregatorCountingModel shouldBe getTestNotificationAggregatorCountingModel()
        }

        "Convert model to dto" {

            val notificationAggregatorCountingModel = getTestNotificationAggregatorCountingModel()

            val notificationAggregatorCountingDto = notificationAggregatorCountingMapper.modelToDto(notificationAggregatorCountingModel)

            notificationAggregatorCountingDto shouldBe getTestNotificationAggregatorCountingDto()
        }

        "Convert dto to model" {

            val notificationAggregatorCountingDto = getTestNotificationAggregatorCountingDto()

            val notificationAggregatorCountingModel = notificationAggregatorCountingMapper.dtoToModel(notificationAggregatorCountingDto)

            notificationAggregatorCountingModel shouldBe getTestNotificationAggregatorCountingModel()
        }
    }
}
