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
    lateinit var notificationAggregatorScheduledMapper: INotificationAggregatorScheduledMapperImpl

    init {

        // TODO: Clean up these tests. We can probably create a higher order util functions that does this

        "Convert model to entity" {

            val notificationAggregatorScheduledModel = getTestAggregatorScheduledModel()

            val notificationAggregatorScheduledEntity = notificationAggregatorScheduledMapper.modelToEntity(notificationAggregatorScheduledModel)

            notificationAggregatorScheduledEntity shouldBe getTestAggregatorScheduledEntity()
        }

        "Convert entity to model" {

            val notificationAggregatorScheduledEntity = getTestAggregatorScheduledEntity()

            val notificationAggregatorScheduledModel = notificationAggregatorScheduledMapper.entityToModel(notificationAggregatorScheduledEntity)

            notificationAggregatorScheduledModel shouldBe getTestAggregatorScheduledModel()
        }

        "Convert model to dto" {

            val notificationAggregatorScheduledModel = getTestAggregatorScheduledModel()

            val notificationAggregatorScheduledDto = notificationAggregatorScheduledMapper.modelToDto(notificationAggregatorScheduledModel)

            notificationAggregatorScheduledDto shouldBe getTestAggregatorScheduledDto()
        }

        "Convert dto to model" {

            val notificationAggregatorScheduledDto = getTestAggregatorScheduledDto()

            val notificationAggregatorScheduledModel = notificationAggregatorScheduledMapper.dtoToModel(notificationAggregatorScheduledDto)

            notificationAggregatorScheduledModel shouldBe getTestAggregatorScheduledModel()
        }
    }
}
