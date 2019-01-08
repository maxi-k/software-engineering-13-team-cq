package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface NotificationRuleMapper {

    fun modelToEntity(notificationRule: NotificationRule): NotificationRuleEntity

    fun entityToModel(notificationRuleEntity: NotificationRuleEntity): NotificationRule

    fun modelToDto(notificationRule: NotificationRule): NotificationRuleDto

    fun dtoToModel(notificationRuleDto: NotificationRuleDto): NotificationRule
}
