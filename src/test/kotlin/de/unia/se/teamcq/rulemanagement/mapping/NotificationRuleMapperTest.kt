package de.unia.se.teamcq.rulemanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleDto
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestUserDto
import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import de.unia.se.teamcq.user.mapping.IUserMapper
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationRuleMapperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var mockIUserMapper: IUserMapper

    @InjectMockKs
    lateinit var notificationRuleMapper: INotificationRuleMapperImpl

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            val expectedUserEntity = getTestUserEntity()
            every { mockIUserMapper.modelToEntity(any()) } returns expectedUserEntity

            val notificationRule = getTestNotificationRuleModel()
            val expectedNotificationRuleEntity = getTestNotificationRuleEntity()

            val notificationRuleEntity = notificationRuleMapper.modelToEntity(notificationRule)

            notificationRuleEntity shouldNotBe null
            notificationRuleEntity.name shouldBe expectedNotificationRuleEntity.name
            notificationRuleEntity.description shouldBe expectedNotificationRuleEntity.description
            notificationRuleEntity.owner shouldBe expectedUserEntity
        }

        "Convert entity to model" {

            every { mockIUserMapper.entityToModel(any()) } returns getTestUserModel()

            val notificationRuleEntity = getTestNotificationRuleEntity()
            val expectedNotificationRuleModel = getTestNotificationRuleModel()

            val notificationRule = notificationRuleMapper.entityToModel(notificationRuleEntity)

            notificationRule shouldNotBe null
            notificationRule.name shouldBe expectedNotificationRuleModel.name
            notificationRule.description shouldBe expectedNotificationRuleModel.description
            notificationRule.owner shouldBe expectedNotificationRuleModel.owner
        }

        "Convert model to dto" {

            every { mockIUserMapper.modelToDto(any()) } returns getTestUserDto()

            val notificationRule = getTestNotificationRuleModel()
            val expectedNotificationRuleDto = getTestNotificationRuleDto()

            val notificationRuleDto = notificationRuleMapper.modelToDto(notificationRule)

            notificationRuleDto shouldNotBe null
            notificationRuleDto.name shouldBe expectedNotificationRuleDto.name
            notificationRuleDto.description shouldBe expectedNotificationRuleDto.description
            notificationRuleDto.owner shouldBe expectedNotificationRuleDto.owner
        }

        "Convert dto to model" {

            every { mockIUserMapper.dtoToModel(any()) } returns getTestUserModel()

            val notificationRuleDto = getTestNotificationRuleDto()
            val expectedNotificationRuleModel = getTestNotificationRuleModel()

            val notificationRule = notificationRuleMapper.dtoToModel(notificationRuleDto)

            notificationRule shouldNotBe null
            notificationRule.name shouldBe expectedNotificationRuleModel.name
            notificationRule.description shouldBe expectedNotificationRuleModel.description
            notificationRule.owner shouldBe expectedNotificationRuleModel.owner
        }
    }
}
