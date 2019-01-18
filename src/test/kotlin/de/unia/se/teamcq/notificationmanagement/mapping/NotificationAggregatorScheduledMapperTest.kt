package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledDto
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NotificationAggregatorScheduledMapperTest : StringSpec() {

    @Autowired
    lateinit var notificationAggregatorScheduledMapper: INotificationAggregatorScheduledMapperImpl

    init {

        // TODO: Clean up these tests. We can probably create a higher order util functions that does this

        "Convert model to entity" {

            val notificationAggregatorScheduledModel = getTestNotificationAggregatorScheduledModel()

            val notificationAggregatorScheduledEntity = notificationAggregatorScheduledMapper.modelToEntity(notificationAggregatorScheduledModel)

            notificationAggregatorScheduledEntity shouldBe getTestNotificationAggregatorScheduledEntity()
        }

        "Convert entity to model" {

            val notificationAggregatorScheduledEntity = getTestNotificationAggregatorScheduledEntity()

            val notificationAggregatorScheduledModel = notificationAggregatorScheduledMapper.entityToModel(notificationAggregatorScheduledEntity)

            notificationAggregatorScheduledModel shouldBe getTestNotificationAggregatorScheduledModel()
        }

        "Convert model to dto" {

            val notificationAggregatorScheduledModel = getTestNotificationAggregatorScheduledModel()

            val notificationAggregatorScheduledDto = notificationAggregatorScheduledMapper.modelToDto(notificationAggregatorScheduledModel)

            notificationAggregatorScheduledDto shouldBe getTestNotificationAggregatorScheduledDto()
        }

        "Convert dto to model" {

            val notificationAggregatorScheduledDto = getTestNotificationAggregatorScheduledDto()

            val notificationAggregatorScheduledModel = notificationAggregatorScheduledMapper.dtoToModel(notificationAggregatorScheduledDto)

            notificationAggregatorScheduledModel shouldBe getTestNotificationAggregatorScheduledModel()
        }
    }
}
