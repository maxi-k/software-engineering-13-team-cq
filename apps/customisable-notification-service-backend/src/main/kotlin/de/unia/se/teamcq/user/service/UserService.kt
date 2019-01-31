package de.unia.se.teamcq.user.service

import de.unia.se.teamcq.user.entity.IUserRepository
import de.unia.se.teamcq.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserService : IUserService {

    @Autowired
    lateinit var userRepository: IUserRepository

    override fun createOrSaveUser(user: User): User? {
        return userRepository.createOrSaveUser(user)
    }

    override fun getOrCreateUser(username: String): User? {
        return userRepository.getOrCreateUser(username)
    }
}
