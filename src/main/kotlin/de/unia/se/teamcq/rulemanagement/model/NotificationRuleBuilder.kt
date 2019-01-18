package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.user.model.User

class NotificationRuleBuilder private constructor() {

    private var ruleId: Long? = 0

    private var name: String? = ""

    private var owner: User? = null

    private var description: String? = null

    private var condition: RuleCondition? = null

    private var aggregator: Aggregator? = null

    private var recipients: Set<Recipient> = mutableSetOf()

    private var ownerAsAdditionalRecipient: Boolean? = true

    fun withId(ruleId: Long): NotificationRuleBuilder = apply { this.ruleId = ruleId }

    fun withName(name: String): NotificationRuleBuilder = apply { this.name = name }

    fun withOwner(user: User): NotificationRuleBuilder = apply { this.owner = user }

    fun withDescription(description: String): NotificationRuleBuilder = apply { this.description = description }

    fun withCondition(ruleCondition: RuleCondition): NotificationRuleBuilder = apply { this.condition = ruleCondition }

    fun withAggregator(aggregator: Aggregator): NotificationRuleBuilder = apply { this.aggregator = aggregator }

    fun withRecipients(recipients: Set<Recipient>): NotificationRuleBuilder = apply { this.recipients = recipients }

    fun withOwnerAsAdditionalRecipient(ownerAsRecipient: Boolean): NotificationRuleBuilder = apply {
        this.ownerAsAdditionalRecipient = ownerAsRecipient
    }

    fun build(): NotificationRule = NotificationRule(ruleId, name, owner, description, condition, aggregator,
            recipients, ownerAsAdditionalRecipient)

    companion object {
        operator fun invoke(): NotificationRuleBuilder {

            return NotificationRuleBuilder()
        }
    }
}
