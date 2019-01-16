package de.unia.se.teamcq.user.service

import de.unia.se.teamcq.user.model.User
import org.springframework.stereotype.Service

@Service
interface IUserService {

    fun createOrSaveUser(user: User): User?

    fun getOrCreateUser(username: String): User?
}
