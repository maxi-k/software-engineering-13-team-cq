package de.unia.se.teamcq.user.controller

import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.entity.UserRepository
import de.unia.se.teamcq.user.mapping.IUserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/notification-rule-management")
class UserSettingsController {

    @Autowired
    lateinit var userMapper: IUserMapper

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/user-settings/")
    fun getUserSettings(): ResponseEntity<UserDto> {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.getOrCreateUser(username)

        return user?.let { existingUser ->
            ResponseEntity.ok(userMapper.modelToDto(existingUser))
        } ?: (ResponseEntity.notFound().build())
    }

    @PutMapping("/user-settings/")
    fun setUserSettings(@Valid @RequestBody userDto: UserDto
    ): ResponseEntity<UserDto> {

        val username = SecurityContextHolder.getContext().authentication.name
        val userWithPotentiallyMissingName = userMapper.dtoToModel(userDto)
        val userToSave = userWithPotentiallyMissingName.copy(name = username)

        val user = userRepository.createOrSaveUser(userToSave)

        return user?.let { existingUser ->
            ResponseEntity.ok(userMapper.modelToDto(existingUser))
        } ?: (ResponseEntity.notFound().build())
    }
}
