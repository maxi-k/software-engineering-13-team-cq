package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import io.kotlintest.specs.StringSpec
import org.assertj.core.api.Assertions.assertThat
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import kotlin.test.assertEquals

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationRuleMapperTest : StringSpec() {

    private var notificationRuleMapper: INotificationRuleMapper = Mappers.getMapper(INotificationRuleMapper::class.java)

    init {

        "convert model to entity" {
            // Perform a POST request to /events/vehicle-status
            // given
            val notificationRule = NotificationRule(
                    2,
                    "name",
                    "description"
            )

            // when
            val notificationRuleEntity = notificationRuleMapper.modelToEntity(notificationRule)

            // then
            assertThat(notificationRuleEntity).isNotNull
            assertEquals(notificationRuleEntity.name, notificationRule.name)
            assertEquals(notificationRuleEntity.description, notificationRule.description)
        }

        "convert entity to model" {
            // Perform a POST request to /events/vehicle-status
            // given
            val notificationRuleEntity = NotificationRuleEntity(
                    2,
                    "name",
                    "description"
            )

            // when
            val notificationRule = notificationRuleMapper.entityToModel(notificationRuleEntity)

            // then
            assertThat(notificationRule).isNotNull
            assertEquals(notificationRuleEntity.name, notificationRule.name)
            assertEquals(notificationRuleEntity.description, notificationRule.description)
        }

        "convert model to dto" {
            // Perform a POST request to /events/vehicle-status
            // given
            val notificationRule = NotificationRule(
                    2,
                    "name",
                    "description"
            )

            // when
            val notificationRuleDto = notificationRuleMapper.modelToDto(notificationRule)

            // then
            assertThat(notificationRuleDto).isNotNull
            assertEquals(notificationRuleDto.name, notificationRule.name)
            assertEquals(notificationRuleDto.description, notificationRule.description)
        }

        "convert dto to model" {
            // given
            val notificationRuleDto = NotificationRuleDto(
                    2,
                    "name",
                    "description"
            )

            // when
            val notificationRule = notificationRuleMapper.dtoToModel(notificationRuleDto)

            // then
            assertThat(notificationRule).isNotNull
            assertEquals(notificationRuleDto.name, notificationRule.name)
            assertEquals(notificationRuleDto.description, notificationRule.description)
        }
    }
}
