package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.notificationmanagement.mapping.IAggregatorMapper
import de.unia.se.teamcq.ruleevaluation.mapping.IRuleConditionMapper
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.mapping.IUserMapper
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [IUserMapper::class, IRuleConditionMapper::class,
    IAggregatorMapper::class])
interface INotificationRuleMapper {

    fun modelToEntity(notificationRule: NotificationRule): NotificationRuleEntity

    fun entityToModel(notificationRuleEntity: NotificationRuleEntity): NotificationRule

    fun modelToDto(notificationRule: NotificationRule): NotificationRuleDto

    fun dtoToModel(notificationRuleDto: NotificationRuleDto): NotificationRule
}
