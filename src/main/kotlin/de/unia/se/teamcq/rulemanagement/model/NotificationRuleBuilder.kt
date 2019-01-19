package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.user.model.User

class NotificationRuleBuilder private constructor() {

    var ruleId: Long? = 0
        private set

    var name: String? = ""
        private set

    var owner: User? = null
        private set

    var description: String? = null
        private set

    var condition: RuleCondition? = null
        private set

    fun withId(ruleId: Long): NotificationRuleBuilder = apply { this.ruleId = ruleId }

    fun withName(name: String): NotificationRuleBuilder = apply { this.name = name }

    fun withOwner(user: User): NotificationRuleBuilder = apply { this.owner = user }

    fun withDescription(description: String): NotificationRuleBuilder = apply { this.description = description }

    fun withCondition(ruleCondition: RuleCondition): NotificationRuleBuilder = apply { this.condition = ruleCondition }

    fun build(): NotificationRule = NotificationRule(ruleId, name, owner, description, condition)

    companion object {
        operator fun invoke(): NotificationRuleBuilder {

            return NotificationRuleBuilder()
        }
    }
}
