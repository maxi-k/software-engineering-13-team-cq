package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.user.model.User
import org.springframework.stereotype.Repository

@Repository
interface IUserRepository {

    fun createOrSaveUser(user: User): User?

    fun getOrCreateUser(username: String): User?
}
