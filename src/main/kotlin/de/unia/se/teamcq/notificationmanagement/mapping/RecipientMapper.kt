package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.RecipientDto
import de.unia.se.teamcq.notificationmanagement.dto.RecipientMailDto
import de.unia.se.teamcq.notificationmanagement.dto.RecipientSmsDto
import de.unia.se.teamcq.notificationmanagement.entity.RecipientEntity
import de.unia.se.teamcq.notificationmanagement.entity.RecipientMailEntity
import de.unia.se.teamcq.notificationmanagement.entity.RecipientSmsEntity
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import de.unia.se.teamcq.notificationmanagement.model.RecipientMail
import de.unia.se.teamcq.notificationmanagement.model.RecipientSms
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RecipientMapper : IRecipientMapper {

    @Autowired
    lateinit var recipientMailMapper: AbstractRecipientMailMapper

    @Autowired
    lateinit var recipientSmsMapper: IRecipientSmsMapper

    override fun modelToEntity(recipient: Recipient): RecipientEntity {
        return when (recipient) {
            is RecipientMail -> recipientMailMapper.modelToEntity(recipient)
            is RecipientSms -> recipientSmsMapper.modelToEntity(recipient)
            else -> throw IllegalArgumentException("Unknown recipient type: $recipient")
        }
    }

    override fun entityToModel(recipientEntity: RecipientEntity): Recipient {
        return when (recipientEntity) {
            is RecipientMailEntity -> recipientMailMapper.entityToModel(recipientEntity)
            is RecipientSmsEntity -> recipientSmsMapper.entityToModel(recipientEntity)
            else -> throw IllegalArgumentException("Unknown recipient type: $recipientEntity")
        }
    }

    override fun modelToDto(recipient: Recipient): RecipientDto {
        return when (recipient) {
            is RecipientMail -> recipientMailMapper.modelToDto(recipient)
            is RecipientSms -> recipientSmsMapper.modelToDto(recipient)
            else -> throw IllegalArgumentException("Unknown recipient type: $recipient")
        }
    }

    override fun dtoToModel(recipientDto: RecipientDto): Recipient {
        return when (recipientDto) {
            is RecipientMailDto -> recipientMailMapper.dtoToModel(recipientDto)
            is RecipientSmsDto -> recipientSmsMapper.dtoToModel(recipientDto)
            else -> throw IllegalArgumentException("Unknown recipient type: $recipientDto")
        }
    }
}
