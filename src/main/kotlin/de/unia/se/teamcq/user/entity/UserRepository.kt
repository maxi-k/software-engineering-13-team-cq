package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.user.mapping.IUserMapper
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.model.UserNotificationType
import de.unia.se.teamcq.user.model.UserSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface IUserEntityRepository : JpaRepository<UserEntity, String>

@Component
class UserRepository : IUserRepository {

    @Autowired
    lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    lateinit var userMapper: IUserMapper

    override fun createOrSaveUser(user: User): User? {

        val userEntityToSave = userMapper.modelToEntity(user)

        // User doesn't have the ownedNotificationRules attribute, so we don't want to override it
        userEntityRepository.findById(user.name!!).orElse(null)
                ?.let { userEntity ->
                    val ownedNotificationRules = userEntity.notificationRules
                    userEntityToSave.notificationRules = ownedNotificationRules
                }

        val savedUserEntity = userEntityRepository.save(userEntityToSave)

        return userMapper.entityToModel(savedUserEntity)
    }

    override fun getOrCreateUser(username: String): User? {
        val userEntity = userEntityRepository.findById(username).orElse(null)

        return userEntity?.let { existingUserEntity -> userMapper.entityToModel(existingUserEntity) }
                ?: return createOrSaveUser(User(username, null, null, UserSettings(0, UserNotificationType.EMAIL)))
    }
}
