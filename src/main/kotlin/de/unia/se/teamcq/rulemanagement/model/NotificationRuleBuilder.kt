package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.user.model.User

class NotificationRuleBuilder private constructor() {

    private var ruleId: Long? = 0

    private var name: String? = ""

    private var owner: User? = null

    private var description: String? = null

    private var condition: RuleCondition? = null

    private var aggregator: Aggregator? = null

    fun withId(ruleId: Long): NotificationRuleBuilder = apply { this.ruleId = ruleId }

    fun withName(name: String): NotificationRuleBuilder = apply { this.name = name }

    fun withOwner(user: User): NotificationRuleBuilder = apply { this.owner = user }

    fun withDescription(description: String): NotificationRuleBuilder = apply { this.description = description }

    fun withCondition(ruleCondition: RuleCondition): NotificationRuleBuilder = apply { this.condition = ruleCondition }

    fun withAggregator(aggregator: Aggregator): NotificationRuleBuilder = apply { this.aggregator = aggregator }

    fun build(): NotificationRule = NotificationRule(ruleId, name, owner, description, condition, aggregator)

    companion object {
        operator fun invoke(): NotificationRuleBuilder {

            return NotificationRuleBuilder()
        }
    }
}
