package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationRuleMapperTest : StringSpec() {

    private var notificationRuleMapper: INotificationRuleMapper = Mappers.getMapper(INotificationRuleMapper::class.java)

    init {

        "convert model to entity" {

            val notificationRule = NotificationRule(
                    2,
                    "name",
                    "description"
            )

            val notificationRuleEntity = notificationRuleMapper.modelToEntity(notificationRule)

            notificationRuleEntity shouldNotBe null
            notificationRuleEntity.name shouldBe notificationRule.name
            notificationRuleEntity.description shouldBe notificationRule.description
        }

        "convert entity to model" {

            val notificationRuleEntity = NotificationRuleEntity(
                    2,
                    "name",
                    "description"
            )

            val notificationRule = notificationRuleMapper.entityToModel(notificationRuleEntity)

            notificationRule shouldNotBe null
            notificationRuleEntity.name shouldBe notificationRule.name
            notificationRuleEntity.description shouldBe notificationRule.description
        }

        "convert model to dto" {

            val notificationRule = NotificationRule(
                    2,
                    "name",
                    "description"
            )

            val notificationRuleDto = notificationRuleMapper.modelToDto(notificationRule)

            notificationRuleDto shouldNotBe null
            notificationRuleDto.name shouldBe notificationRule.name
            notificationRuleDto.description shouldBe notificationRule.description
        }

        "convert dto to model" {

            val notificationRuleDto = NotificationRuleDto(
                    2,
                    "name",
                    "description"
            )

            val notificationRule = notificationRuleMapper.dtoToModel(notificationRuleDto)

            notificationRule shouldNotBe null
            notificationRuleDto.name shouldBe notificationRule.name
            notificationRuleDto.description shouldBe notificationRule.description
        }
    }
}
