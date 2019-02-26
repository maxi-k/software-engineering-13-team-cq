package de.unia.se.teamcq.user.mapping

import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.entity.UserEntity
import de.unia.se.teamcq.user.model.User
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class AbstractUserMapper {

    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(userDto: UserDto) {

        if (userDto.userSettings == null) {
            throw IllegalArgumentException("Attribute userSettings of UserDto" +
                    " is required but was null!")
        }

        if (userDto.userSettings!!.locale == null) {
            throw IllegalArgumentException("Attribute locale of UserSettingsDto of" +
                    " UserDto is required but was null!")
        }

        if (userDto.userSettings!!.userNotificationType == null) {
            throw IllegalArgumentException("Attribute userNotificationType of" +
                    "UserSettingsDto of UserDto is required but was null!")
        }
    }

    abstract fun modelToEntity(user: User): UserEntity

    abstract fun entityToModel(userEntity: UserEntity): User

    abstract fun modelToDto(user: User): UserDto

    abstract fun dtoToModel(userDto: UserDto): User
}
