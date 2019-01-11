package de.unia.se.teamcq.rulemanagement.model

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

    fun withId(ruleId: Long): NotificationRuleBuilder = apply { this.ruleId = ruleId }

    fun withName(name: String): NotificationRuleBuilder = apply { this.name = name }

    fun withOwner(user: User): NotificationRuleBuilder = apply { this.owner = user }

    fun withDescription(description: String): NotificationRuleBuilder = apply { this.description = description }

    fun build(): NotificationRule = NotificationRule(ruleId, name, owner, description)

    companion object {
        operator fun invoke(): NotificationRuleBuilder {

            return NotificationRuleBuilder()
        }
    }
}
