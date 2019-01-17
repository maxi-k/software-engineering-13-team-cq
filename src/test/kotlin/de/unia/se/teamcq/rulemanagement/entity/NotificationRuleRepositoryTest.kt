package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionCompositeEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
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

        "GetAllNotificationRulesForUser should work" {

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

            val expectedNotificationRule = getTestNotificationRuleModel().apply {
                owner!!.name = "test1"
                ruleId = savedNotificationRuleEntity.ruleId
                setConditionIds(savedNotificationRuleEntity)
            }
            notificationRulesForUser.first() shouldBe expectedNotificationRule
        }

        "GetNotificationRule should return NotificationRule if value is present" {

            userEnityRepository.save(getTestUserEntity())

            val savedNotificationRuleEntity = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())

            val actualNotificationRule = notificationRuleRepository.getNotificationRule(savedNotificationRuleEntity.ruleId!!)
            val expectedNotificationRule = getTestNotificationRuleModel().apply {
                ruleId = savedNotificationRuleEntity.ruleId!!
                setConditionIds(savedNotificationRuleEntity)
            }

            actualNotificationRule shouldBe expectedNotificationRule
        }

        "GetNotificationRule should return null if value is not present" {

            val actualNotificationRule = notificationRuleRepository.getNotificationRule(1059)

            actualNotificationRule shouldBe null
        }

        "CreateNotificationRule should work" {

            userEnityRepository.save(getTestUserEntity())

            val savedNotificationRule = notificationRuleRepository.createNotificationRule(getTestNotificationRuleModel())

            val expectedNotificationRule = getTestNotificationRuleModel().apply {
                ruleId = savedNotificationRule!!.ruleId!!
                setConditionIds(savedNotificationRule)
            }

            savedNotificationRule shouldBe expectedNotificationRule
        }

        "UpdateNotificationRule should work and not overwrite user" {

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
                setConditionIds(savedNotificationRuleEntity)
            }

            val updatedNotificationRule = notificationRuleRepository.updateNotificationRule(newNotificationRuleWithNewUser)!!

            updatedNotificationRule.copy(owner = null) shouldBe newNotificationRuleWithNewUser.copy(owner = null)
            updatedNotificationRule.owner shouldBe oldUserModel
        }

        "DeleteNotificationRule should work" {

            userEnityRepository.save(getTestUserEntity())

            val savedUserEntity = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())

            notificationRuleRepository.deleteNotificationRule(savedUserEntity.ruleId!!)

            notificationRuleEntityRepository.existsById(savedUserEntity.ruleId!!) shouldBe false
        }
    }

    private fun NotificationRule.setConditionIds(savedNotificationRule: NotificationRule) {
        condition!!.conditionId = savedNotificationRule.condition!!.conditionId

        val ruleConditionComposite = condition!! as RuleConditionComposite
        val savedConditionComposite = savedNotificationRule.condition!! as RuleConditionComposite
        ruleConditionComposite.subConditions[0].conditionId = savedConditionComposite.subConditions[0].conditionId
    }

    private fun NotificationRule.setConditionIds(savedNotificationRuleEntity: NotificationRuleEntity) {

        condition!!.conditionId = savedNotificationRuleEntity.condition!!.conditionId

        val ruleConditionComposite = condition!! as RuleConditionComposite
        val savedConditionComposite = savedNotificationRuleEntity.condition!! as RuleConditionCompositeEntity
        ruleConditionComposite.subConditions[0].conditionId = savedConditionComposite.subConditions[0].conditionId
    }
}
