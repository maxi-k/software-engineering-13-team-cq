package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.RecipientMailDto
import de.unia.se.teamcq.notificationmanagement.entity.RecipientMailEntity
import de.unia.se.teamcq.notificationmanagement.model.RecipientMail
import org.apache.commons.validator.routines.EmailValidator
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class AbstractRecipientMailMapper {

    @BeforeMapping
    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(recipientMailDto: RecipientMailDto) {

        val emailValidator = EmailValidator.getInstance()

        if (!emailValidator.isValid(recipientMailDto.mailAddress)) {
            throw IllegalArgumentException("Attribute MailAddress of RecipientMailDto" +
                    " must be a valid Email Address but was ${recipientMailDto.mailAddress}")
        }
    }

    abstract fun modelToDto(recipientMail: RecipientMail): RecipientMailDto

    abstract fun dtoToModel(recipientMailDto: RecipientMailDto): RecipientMail

    abstract fun modelToEntity(recipientMail: RecipientMail): RecipientMailEntity

    abstract fun entityToModel(recipientMailEntity: RecipientMailEntity): RecipientMail
}
