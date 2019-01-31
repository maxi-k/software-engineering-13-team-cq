package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.RecipientSmsDto
import de.unia.se.teamcq.notificationmanagement.entity.RecipientSmsEntity
import de.unia.se.teamcq.notificationmanagement.model.RecipientSms
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IRecipientSmsMapper {

    fun modelToDto(recipientSms: RecipientSms): RecipientSmsDto

    fun dtoToModel(recipientSmsDto: RecipientSmsDto): RecipientSms

    fun modelToEntity(recipientSms: RecipientSms): RecipientSmsEntity

    fun entityToModel(recipientSmsEntity: RecipientSmsEntity): RecipientSms
}
