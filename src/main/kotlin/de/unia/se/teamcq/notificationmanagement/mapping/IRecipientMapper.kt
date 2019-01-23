package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.RecipientDto
import de.unia.se.teamcq.notificationmanagement.entity.RecipientEntity
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import org.springframework.stereotype.Service

@Service
interface IRecipientMapper {

    fun modelToEntity(recipient: Recipient): RecipientEntity

    fun entityToModel(recipientEntity: RecipientEntity): Recipient

    fun modelToDto(recipient: Recipient): RecipientDto

    fun dtoToModel(recipientDto: RecipientDto): Recipient
}
