package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.user.model.User

data class NotificationRule(
        var ruleId: Long? = 0,

        var name: String?,

        var owner: User?,

        var description: String?,

        var condition: RuleCondition?,

        var aggregator: Aggregator?,

        var recipients: Set<Recipient>,

        var ownerAsAdditionalRecipient: Boolean?

        ) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null, null, mutableSetOf<Recipient>(), null)
}
