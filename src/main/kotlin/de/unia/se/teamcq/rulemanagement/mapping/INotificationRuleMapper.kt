package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.RecipientDto
import de.unia.se.teamcq.notificationmanagement.entity.RecipientEntity
import de.unia.se.teamcq.notificationmanagement.mapping.IAggregatorMapper
import de.unia.se.teamcq.notificationmanagement.mapping.IRecipientMapper
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import de.unia.se.teamcq.ruleevaluation.mapping.IRuleConditionMapper
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.mapping.IUserMapper
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", uses = [IUserMapper::class, IRuleConditionMapper::class,
    IAggregatorMapper::class, RecipientMapperHelper::class])
interface INotificationRuleMapper {

    fun modelToEntity(notificationRule: NotificationRule): NotificationRuleEntity

    fun entityToModel(notificationRuleEntity: NotificationRuleEntity): NotificationRule

    fun modelToDto(notificationRule: NotificationRule): NotificationRuleDto

    fun dtoToModel(notificationRuleDto: NotificationRuleDto): NotificationRule
}

// Mapstruct seems to fail converting sets if the contained class is abstract
@Component
class RecipientMapperHelper {

    @Autowired
    lateinit var recipientMapper: IRecipientMapper

    fun modelToEntity(list: List<Recipient>): List<RecipientEntity> {

        return list.map { recipient -> recipientMapper.modelToEntity(recipient) }
    }

    fun entityToModel(list: List<RecipientEntity>): List<Recipient> {

        return list.map { recipient -> recipientMapper.entityToModel(recipient) }
    }

    fun modelToDto(list: List<Recipient>): List<RecipientDto> {

        return list.map { recipient -> recipientMapper.modelToDto(recipient) }
    }

    fun dtoToModel(list: List<RecipientDto>): List<Recipient> {

        return list.map { recipient -> recipientMapper.dtoToModel(recipient) }
    }
}
