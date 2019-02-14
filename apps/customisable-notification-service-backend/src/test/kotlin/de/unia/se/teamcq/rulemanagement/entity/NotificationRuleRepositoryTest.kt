package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestRuleConditionEntityWithGreaterDepth
import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionCompositeEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.entity.IUserEntityRepository
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.withClue
import io.kotlintest.should
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
    lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    lateinit var notificationRuleRepository: INotificationRuleRepository

    init {
        MockKAnnotations.init(this)

        "GetAllNotificationRulesForUser should work" {

            val userEntityA = getTestUserEntity().apply { name = "test1" }
            val userEntityB = getTestUserEntity().apply { name = "test2" }
            val notificationRuleEntityA = getTestNotificationRuleEntity().copy(ruleId = 1, owner = userEntityA)
            val notificationRuleEntityB = getTestNotificationRuleEntity().copy(ruleId = 2, owner = userEntityB)

            userEntityRepository.save(userEntityA)
            userEntityRepository.save(userEntityB)

            val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntityA)
            notificationRuleEntityRepository.save(notificationRuleEntityB)

            val notificationRulesForUser = notificationRuleRepository.getAllNotificationRulesForUser("test1")

            notificationRulesForUser.size shouldBe 1

            val expectedNotificationRule = getTestNotificationRuleModel().apply {
                owner!!.name = "test1"
                setIdsOfRelatedRepositoryEntities(savedNotificationRuleEntity)
            }
            notificationRulesForUser.first() shouldBe expectedNotificationRule
        }

        "GetNotificationRule should return NotificationRule if value is present" {

            userEntityRepository.save(getTestUserEntity())

            val savedNotificationRuleEntity = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())

            val actualNotificationRule = notificationRuleRepository.getNotificationRule(savedNotificationRuleEntity.ruleId!!)
            val expectedNotificationRule = getTestNotificationRuleModel().apply {
                setIdsOfRelatedRepositoryEntities(savedNotificationRuleEntity)
            }

            actualNotificationRule shouldBe expectedNotificationRule
        }

        "GetNotificationRule should return null if value is not present" {

            val actualNotificationRule = notificationRuleRepository.getNotificationRule(1059)

            actualNotificationRule shouldBe null
        }

        "CreateNotificationRule should work" {

            userEntityRepository.save(getTestUserEntity())

            val savedNotificationRule = notificationRuleRepository.createNotificationRule(getTestNotificationRuleModel())

            val expectedNotificationRule = getTestNotificationRuleModel().apply {
                setIdsOfRelatedRepositoryEntities(savedNotificationRule!!)
            }

            savedNotificationRule shouldBe expectedNotificationRule

            withClue("rule fleet ordering should be persisted") {
                savedNotificationRule!!.affectedFleets.forEachIndexed { index, fleetEntity ->
                    fleetEntity shouldBe expectedNotificationRule.affectedFleets[index]
                }
            }
        }

        // TODO: Does *not* fail when incorrectly using EntityManager#merge in the resository save function yet
        "CreateNotificationRule should use existing fleets" {

            userEntityRepository.save(getTestUserEntity())

            val rule1 = getTestNotificationRuleModel()
            val rule2 = getTestNotificationRuleModel()

            withClue("rules should be structurally equal") {
                rule1 shouldBe rule2
                rule1.affectedFleets[0] shouldBe rule2.affectedFleets[0]
            }
            withClue("rules should not be identical") {
                (rule1 === rule2).shouldBeFalse()
                (rule1.affectedFleets[0] === rule2.affectedFleets[0]).shouldBeFalse()
            }

            val savedNotificationRule1 = notificationRuleRepository.createNotificationRule(rule1)
            // should fail when not using entity manager
            val savedNotificationRule2 = notificationRuleRepository.createNotificationRule(rule2)

            val expectedNotificationRule1 = getTestNotificationRuleModel().apply {
                setIdsOfRelatedRepositoryEntities(savedNotificationRule1!!)
            }
            val expectedNotificationRule2 = getTestNotificationRuleModel().apply {
                setIdsOfRelatedRepositoryEntities(savedNotificationRule2!!)
            }

            savedNotificationRule1 shouldBe expectedNotificationRule1
            savedNotificationRule2 shouldBe expectedNotificationRule2

            // savedNotificationRule1.affectedFleets[0] shouldBe savedNotificationRule2.affectedFleets[0]
        }

        "UpdateNotificationRule" should {
            "Update and not overwrite user" {

                val oldUserEntity = getTestUserEntity().apply { mailAddress = "test1" }
                val savedOldUserEntity = userEntityRepository.save(oldUserEntity)
                val oldUserModel = getTestUserModel().apply {
                    mailAddress = "test1"
                    userSettings!!.settingsId = savedOldUserEntity.userSettings!!.settingsId
                }

                val notificationRuleWithOldUser = getTestNotificationRuleEntity().copy(owner = oldUserEntity)
                val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleWithOldUser)

                val newUser = getTestUserModel().apply { mailAddress = "" }
                val newNotificationRuleWithNewUser = getTestNotificationRuleModel().apply {
                    description = "new"
                    setIdsOfRelatedRepositoryEntities(savedNotificationRuleEntity)
                    owner = newUser
                }

                val updatedNotificationRule = notificationRuleRepository.updateNotificationRule(newNotificationRuleWithNewUser)!!

                updatedNotificationRule.copy(owner = null) shouldBe newNotificationRuleWithNewUser.copy(owner = null)
                updatedNotificationRule.owner shouldBe oldUserModel
            }

            "Update conditions" {

                val userEntity = getTestUserEntity()
                val notificationRuleWithOldCondition = getTestNotificationRuleEntity()
                        .copy(condition = getTestRuleConditionEntityWithGreaterDepth())

                userEntityRepository.save(userEntity)
                val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleWithOldCondition)

                val newNotificationRuleWithNewCondition = getTestNotificationRuleModel().apply {
                    setIdsOfRelatedRepositoryEntities(savedNotificationRuleEntity)
                }

                val updatedNotificationRule = notificationRuleRepository.updateNotificationRule(newNotificationRuleWithNewCondition)!!

                val expectedNotificationRule = newNotificationRuleWithNewCondition
                        // Ids of Subclasses of abstract classes may have changed
                        .apply { setIdsOfRelatedRepositoryEntities(updatedNotificationRule) }

                updatedNotificationRule shouldBe expectedNotificationRule
            }

            "Update aggregators" {

                val userEntity = getTestUserEntity()
                val notificationRuleWithOldAggregator = getTestNotificationRuleEntity()
                        .copy(aggregator = getTestAggregatorCountingEntity())

                userEntityRepository.save(userEntity)
                val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleWithOldAggregator)

                val newNotificationRuleWithNewAggregator = getTestNotificationRuleModel().apply {
                    setIdsOfRelatedRepositoryEntities(savedNotificationRuleEntity)
                }

                val updatedNotificationRule = notificationRuleRepository.updateNotificationRule(newNotificationRuleWithNewAggregator)!!

                val expectedNotificationRule = newNotificationRuleWithNewAggregator
                        // Ids of Subclasses of abstract classes may have changed
                        .apply { setIdsOfRelatedRepositoryEntities(updatedNotificationRule) }

                updatedNotificationRule shouldBe expectedNotificationRule
            }
        }

        "DeleteNotificationRule should work" {

            userEntityRepository.save(getTestUserEntity())

            val savedUserEntity = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())

            notificationRuleRepository.deleteNotificationRule(savedUserEntity.ruleId!!)

            notificationRuleEntityRepository.existsById(savedUserEntity.ruleId!!) shouldBe false
        }
    }

    private fun NotificationRule.setIdsOfRelatedRepositoryEntities(savedNotificationRule: NotificationRule) {
        ruleId = savedNotificationRule.ruleId!!

        condition!!.conditionId = savedNotificationRule.condition!!.conditionId

        val ruleConditionComposite = condition!! as RuleConditionComposite
        val savedConditionComposite = savedNotificationRule.condition!! as RuleConditionComposite
        ruleConditionComposite.subConditions[0].conditionId = savedConditionComposite.subConditions[0].conditionId

        aggregator!!.aggregatorId = savedNotificationRule.aggregator!!.aggregatorId

        recipients.zip(savedNotificationRule.recipients).forEach { (recipientWithoutId, recipientWithId) ->
            recipientWithoutId.recipientId = recipientWithId.recipientId
        }

        owner!!.userSettings!!.settingsId = savedNotificationRule.owner!!.userSettings!!.settingsId
    }
}

private fun NotificationRule.setIdsOfRelatedRepositoryEntities(savedNotificationRuleEntity: NotificationRuleEntity) {

    ruleId = savedNotificationRuleEntity.ruleId
    condition!!.conditionId = savedNotificationRuleEntity.condition!!.conditionId

    val ruleConditionComposite = condition!! as RuleConditionComposite
    val savedConditionComposite = savedNotificationRuleEntity.condition!! as RuleConditionCompositeEntity
    ruleConditionComposite.subConditions[0].conditionId = savedConditionComposite.subConditions[0].conditionId

    aggregator!!.aggregatorId = savedNotificationRuleEntity.aggregator!!.aggregatorId

    recipients.zip(savedNotificationRuleEntity.recipients!!).forEach { (recipientWithoutId, recipientWithId) ->
        recipientWithoutId.recipientId = recipientWithId.recipientId
    }

    owner!!.userSettings!!.settingsId = savedNotificationRuleEntity.owner!!.userSettings!!.settingsId
}
