package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyDto
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationAggregatorImmediatelyMapperTest : StringSpec() {

    private var notificationAggregatorImmediatelyMapper = Mappers.getMapper(INotificationAggregatorImmediatetlyMapper::class.java)

    init {

        // TODO: Clean up these tests. We can probably create a higher order util functions that does this

        "Convert model to entity" {

            val notificationAggregatorImmediatelyModel = getTestNotificationAggregatorImmediatelyModel()

            val notificationAggregatorImmediatelyEntity = notificationAggregatorImmediatelyMapper.modelToEntity(notificationAggregatorImmediatelyModel)

            notificationAggregatorImmediatelyEntity shouldBe getTestNotificationAggregatorImmediatelyEntity()
        }

        "Convert entity to model" {

            val notificationAggregatorImmediatelyEntity = getTestNotificationAggregatorImmediatelyEntity()

            val notificationAggregatorImmediatelyModel = notificationAggregatorImmediatelyMapper.entityToModel(notificationAggregatorImmediatelyEntity)

            notificationAggregatorImmediatelyModel shouldBe getTestNotificationAggregatorImmediatelyModel()
        }

        "Convert model to dto" {

            val notificationAggregatorImmediatlyModel = getTestNotificationAggregatorImmediatelyModel()

            val notificationAggregatorImmediatelyDto = notificationAggregatorImmediatelyMapper.modelToDto(notificationAggregatorImmediatlyModel)

            notificationAggregatorImmediatelyDto shouldBe getTestNotificationAggregatorImmediatelyDto()
        }

        "Convert dto to model" {

            val notificationAggregatorImmediatelyDto = getTestNotificationAggregatorImmediatelyDto()

            val notificationAggregatorImmediatelyModel = notificationAggregatorImmediatelyMapper.dtoToModel(notificationAggregatorImmediatelyDto)

            notificationAggregatorImmediatelyModel shouldBe getTestNotificationAggregatorImmediatelyModel()
        }
    }
}
