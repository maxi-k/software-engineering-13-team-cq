package de.unia.se.teamcq.rulemanagement.entity

import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationRuleRepositoryTest : StringSpec() {

    @Autowired
    lateinit var notificationRuleRepository: INotificationRuleRepository

    init {
        MockKAnnotations.init(this)

        "get all should work" {
            notificationRuleRepository.getAllNotificationRulesForUser("user_name")
        }
    }
}
