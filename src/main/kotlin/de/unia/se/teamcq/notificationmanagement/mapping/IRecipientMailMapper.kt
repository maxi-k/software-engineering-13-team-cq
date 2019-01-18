package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.RecipientMailDto
import de.unia.se.teamcq.notificationmanagement.entity.RecipientMailEntity
import de.unia.se.teamcq.notificationmanagement.model.RecipientMail
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IRecipientMailMapper {

    fun modelToDto(recipientMail: RecipientMail): RecipientMailDto

    fun dtoToModel(recipientMailDto: RecipientMailDto): RecipientMail

    fun modelToEntity(recipientMail: RecipientMail): RecipientMailEntity

    fun entityToModel(recipientMailEntity: RecipientMailEntity): RecipientMail
}
