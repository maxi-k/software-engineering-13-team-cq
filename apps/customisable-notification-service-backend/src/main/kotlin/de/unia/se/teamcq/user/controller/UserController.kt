package de.unia.se.teamcq.user.controller

import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.mapping.IUserMapper
import de.unia.se.teamcq.user.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user-notification-settings")
class UserController {

    @Autowired
    lateinit var userMapper: IUserMapper

    @Autowired
    lateinit var userService: IUserService

    @GetMapping
    fun getUserSettings(): ResponseEntity<UserDto> {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userService.getOrCreateUser(username)
        return user?.let { existingUser ->
            ResponseEntity.ok(userMapper.modelToDto(existingUser))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping
    fun setUserSettings(@Valid @RequestBody userDto: UserDto): ResponseEntity<UserDto> {

        val username = SecurityContextHolder.getContext().authentication.name
        val userWithPotentiallyMissingName = userMapper.dtoToModel(userDto)
        val userToSave = userWithPotentiallyMissingName.copy(name = username)

        val user = userService.createOrSaveUser(userToSave)

        return user?.let { existingUser ->
            ResponseEntity.ok(userMapper.modelToDto(existingUser))
        } ?: ResponseEntity.notFound().build()
    }
}
