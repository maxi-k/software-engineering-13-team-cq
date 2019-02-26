package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.user.mapping.AbstractUserMapper
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.model.UserSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
interface IUserEntityRepository : JpaRepository<UserEntity, String>

@Component
@Transactional
class UserRepository : IUserRepository {

    @Autowired
    lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    lateinit var userMapper: AbstractUserMapper

    override fun createOrSaveUser(user: User): User? {

        // Use merge so that the persistence layer does not
        // try to create existing entities this references,
        // but instead uses the already existing ones.
        val userEntityToSave = entityManager.merge(
                userMapper.modelToEntity(user)
        )

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
                ?: return createOrSaveUser(User(username, null, null, UserSettings.DEFAULT_USER_SETTINGS))
    }
}
