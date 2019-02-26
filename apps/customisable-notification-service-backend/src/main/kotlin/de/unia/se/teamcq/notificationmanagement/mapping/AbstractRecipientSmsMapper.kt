package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.RecipientSmsDto
import de.unia.se.teamcq.notificationmanagement.entity.RecipientSmsEntity
import de.unia.se.teamcq.notificationmanagement.model.RecipientSms
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class AbstractRecipientSmsMapper {

    @BeforeMapping
    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(recipientSmsDto: RecipientSmsDto) {

        if (recipientSmsDto.phoneNumber.isNullOrBlank()) {
            throw IllegalArgumentException("Attribute phoneNumber of RecipientSmsDto" +
                    " is required but was null or blank!")
        }
    }

    abstract fun modelToDto(recipientSms: RecipientSms): RecipientSmsDto

    abstract fun dtoToModel(recipientSmsDto: RecipientSmsDto): RecipientSms

    abstract fun modelToEntity(recipientSms: RecipientSms): RecipientSmsEntity

    abstract fun entityToModel(recipientSmsEntity: RecipientSmsEntity): RecipientSms
}
