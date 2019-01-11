package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import de.unia.se.teamcq.user.entity.IUserEntityRepository
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class NotificationRuleRepositoryTest : StringSpec() {

    @Autowired
    lateinit var notificationRuleEntityRepository: INotificationRuleEntityRepository

    @Autowired
    lateinit var userEnityRepository: IUserEntityRepository

    @Autowired
    lateinit var notificationRuleRepository: INotificationRuleRepository

    init {
        MockKAnnotations.init(this)

        "getAllNotificationRulesForUser should work" {

            val userEntityA = getTestUserEntity().apply { name = "test1" }
            val userEntityB = getTestUserEntity().apply { name = "test2" }
            val notificationRuleEntityA = getTestNotificationRuleEntity().copy(ruleId = 1, owner = userEntityA)
            val notificationRuleEntityB = getTestNotificationRuleEntity().copy(ruleId = 2, owner = userEntityB)

            userEnityRepository.save(userEntityA)
            userEnityRepository.save(userEntityB)

            val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityA)
            notificationRuleEntityRepository.save(notificationRuleEntityB)

            val notificationRulesForUser = notificationRuleRepository.getAllNotificationRulesForUser("test1")

            notificationRulesForUser.size shouldBe 1

            val expextedNotificationRule = getTestNotificationRuleModel().apply {
                owner!!.name = "test1"
                ruleId = savedNotificationRuleEntity.ruleId
            }
            notificationRulesForUser.first() shouldBe expextedNotificationRule
        }

        "getNotificationRule should return NotificationRule if value is present" {

            userEnityRepository.save(getTestUserEntity())

            val savedNotificationRule = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())

            val actualNotificationRule = notificationRuleRepository.getNotificationRule(savedNotificationRule.ruleId!!)

            actualNotificationRule shouldBe getTestNotificationRuleModel().copy(ruleId = savedNotificationRule.ruleId!!)
        }

        "getNotificationRule should return null if value is not present" {

            val actualNotificationRule = notificationRuleRepository.getNotificationRule(1059)

            actualNotificationRule shouldBe null
        }

        "createNotificationRule should work" {

            userEnityRepository.save(getTestUserEntity())

            val savedNotificationRule = notificationRuleRepository.createNotificationRule(getTestNotificationRuleModel())

            savedNotificationRule shouldBe getTestNotificationRuleModel().copy(ruleId = savedNotificationRule!!.ruleId!!)
        }

        "updateNotificationRule should work and not overwrite user" {

            val oldUserEntity = getTestUserEntity().apply { mailAddress = "test1" }
            val oldUserModel = getTestUserModel().apply { mailAddress = "test1" }
            val notificationRuleWithOldUser = getTestNotificationRuleEntity().copy(owner = oldUserEntity)

            userEnityRepository.save(oldUserEntity)
            val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleWithOldUser)

            val newUser = getTestUserModel().apply { mailAddress = "" }
            val newNotificationRuleWithNewUser = getTestNotificationRuleModel().apply {
                description = "new"
                owner = newUser
                ruleId = savedNotificationRuleEntity.ruleId
            }

            val updatedNotificationRule = notificationRuleRepository.updateNotificationRule(newNotificationRuleWithNewUser)!!

            updatedNotificationRule.copy(owner = null) shouldBe newNotificationRuleWithNewUser.copy(owner = null)
            updatedNotificationRule.owner shouldBe oldUserModel
        }

        "deleteNotificationRule should work" {

            userEnityRepository.save(getTestUserEntity())

            val savedUserEntity = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())

            notificationRuleRepository.deleteNotificationRule(savedUserEntity.ruleId!!)

            notificationRuleEntityRepository.existsById(savedUserEntity.ruleId!!) shouldBe false
        }
    }
}
