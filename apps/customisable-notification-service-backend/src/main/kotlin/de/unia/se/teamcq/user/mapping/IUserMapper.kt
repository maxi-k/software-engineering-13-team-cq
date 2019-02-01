package de.unia.se.teamcq.user.mapping

import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.entity.UserEntity
import de.unia.se.teamcq.user.model.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IUserMapper {

    fun modelToEntity(user: User): UserEntity

    fun entityToModel(userEntity: UserEntity): User

    fun modelToDto(user: User): UserDto

    fun dtoToModel(userDto: UserDto): User
}
