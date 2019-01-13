package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleDto
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.user.mapping.IUserMapper
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationRuleMapperTest : StringSpec() {

    @MockK(relaxed = true)
    private lateinit var mockIUserMapper: IUserMapper

    @InjectMockKs
    lateinit var notificationRuleMapper: INotificationRuleMapperImpl

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            val notificationRule = getTestNotificationRuleModel()

            val notificationRuleEntity = notificationRuleMapper.modelToEntity(notificationRule)

            notificationRuleEntity shouldNotBe null
            notificationRuleEntity.name shouldBe notificationRule.name
            notificationRuleEntity.description shouldBe notificationRule.description
        }

        "Convert entity to model" {

            val notificationRuleEntity = getTestNotificationRuleEntity()

            val notificationRule = notificationRuleMapper.entityToModel(notificationRuleEntity)

            notificationRule shouldNotBe null
            notificationRuleEntity.name shouldBe notificationRule.name
            notificationRuleEntity.description shouldBe notificationRule.description
        }

        "Convert model to dto" {

            val notificationRule = getTestNotificationRuleModel()

            val notificationRuleDto = notificationRuleMapper.modelToDto(notificationRule)

            notificationRuleDto shouldNotBe null
            notificationRuleDto.name shouldBe notificationRule.name
            notificationRuleDto.description shouldBe notificationRule.description
        }

        "Convert dto to model" {

            val notificationRuleDto = getTestNotificationRuleDto()

            val notificationRule = notificationRuleMapper.dtoToModel(notificationRuleDto)

            notificationRule shouldNotBe null
            notificationRuleDto.name shouldBe notificationRule.name
            notificationRuleDto.description shouldBe notificationRule.description
        }
    }
}
