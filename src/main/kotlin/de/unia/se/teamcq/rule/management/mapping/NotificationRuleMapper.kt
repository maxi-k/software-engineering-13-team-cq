package de.unia.se.teamcq.rule.management.mapping

import de.unia.se.teamcq.rule.management.dto.NotificationRuleDto
import de.unia.se.teamcq.rule.management.entity.NotificationRuleEntity
import de.unia.se.teamcq.rule.management.model.NotificationRule
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface NotificationRuleMapper {

    fun modelToEntity(notificationRule: NotificationRule): NotificationRuleEntity

    fun entityToModel(notificationRuleEntity: NotificationRuleEntity): NotificationRule

    fun modelToDto(notificationRule: NotificationRule): NotificationRuleDto

    fun dtoToModel(notificationRuleDto: NotificationRuleDto): NotificationRule
}
