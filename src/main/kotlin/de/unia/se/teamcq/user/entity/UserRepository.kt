package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.mapping.IUserMapper
import de.unia.se.teamcq.user.model.User
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

        val userEntityToSaveWithoutOwnedNotificationRules = userMapper.modelToEntity(user)

        val ownedNotificationRules =  userEntityRepository.findById(user.name!!).orElseGet(null).notificationRules
        val userEntityToSave = userEntityToSaveWithoutOwnedNotificationRules.copy(notificationRules = ownedNotificationRules)

        val savedUserEntity = userEntityRepository.save(userEntityToSave)

        return userMapper.entityToModel(savedUserEntity)
    }

    override fun getOrCreateUser(username: String): User? {
        val userEntity = userEntityRepository.findById(username).orElseGet(null)

        return userEntity?.let { existingUserEntity -> userMapper.entityToModel(existingUserEntity) } ?:
            return createOrSaveUser(User(username, null, null, null))
    }
}
