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
import de.unia.se.teamcq.user.mapping.AbstractUserMapper
import de.unia.se.teamcq.vehiclestate.mapping.AbstractFleetReferenceMapper
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", uses = [AbstractUserMapper::class, IRuleConditionMapper::class,
    IAggregatorMapper::class, RecipientMapperHelper::class, AbstractFleetReferenceMapper::class],
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class AbstractNotificationRuleMapper {

    @BeforeMapping
    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(notificationRuleDto: NotificationRuleDto) {

        if (notificationRuleDto.affectingAllApplicableFleets == null) {
            throw IllegalArgumentException("Attribute affectingAllApplicableFleets of" +
                    " NotificationRuleDto is required but was null!")
        }

        if (notificationRuleDto.ownerAsAdditionalRecipient == null) {
            throw IllegalArgumentException("Attribute ownerAsAdditionalRecipient of" +
                    " NotificationRuleDto is required but was null!")
        }

        if (notificationRuleDto.description == null) {
            throw IllegalArgumentException("Attribute description of NotificationRuleDto" +
                    " is required but was null!")
        }

        if (notificationRuleDto.aggregator == null) {
            throw IllegalArgumentException("Attribute aggregator of" +
                    " NotificationRuleDto is required but was null!")
        }

        if (notificationRuleDto.condition == null) {
            throw IllegalArgumentException("Attribute condition of" +
                    " NotificationRuleDto is required but was null!")
        }

        if (notificationRuleDto.name == null) {
            throw IllegalArgumentException("Attribute name of NotificationRuleDto" +
                    " is required but was null!")
        }

        if (notificationRuleDto.ownerAsAdditionalRecipient == false &&
                notificationRuleDto.recipients.isNullOrEmpty()) {
            throw IllegalArgumentException("NotificationRuleDto has to have some" +
                    " potential recipient but neither the owner nor an additional" +
                    " recipient was provided!")
        }
    }

    abstract fun modelToEntity(notificationRule: NotificationRule): NotificationRuleEntity

    abstract fun entityToModel(notificationRuleEntity: NotificationRuleEntity): NotificationRule

    abstract fun modelToDto(notificationRule: NotificationRule): NotificationRuleDto

    @Throws(IllegalArgumentException::class)
    abstract fun dtoToModel(notificationRuleDto: NotificationRuleDto): NotificationRule
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
