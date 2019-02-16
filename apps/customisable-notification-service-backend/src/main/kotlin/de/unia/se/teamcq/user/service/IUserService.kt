package de.unia.se.teamcq.user.service

import de.unia.se.teamcq.user.model.User
import org.springframework.stereotype.Service

/**
 * The central CRUD service for [User]s.
 */
@Service
interface IUserService {

    /**
     * Creates or updates a [User]
     *
     * @param user The user to create or update
     * @returns The created/updated user if everything worked
     */
    fun createOrSaveUser(user: User): User?

    /**
     * Get or creates a [User]
     *
     * If we encounter a user that exists within the other micro services,
     * we always want to create it in our micro service as well.
     *
     * @param username The name of the user
     * @returns The user if he exists or created it worked
     */
    fun getOrCreateUser(username: String): User?
}
