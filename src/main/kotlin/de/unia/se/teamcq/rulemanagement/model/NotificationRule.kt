package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.user.model.User

data class NotificationRule(
    var ruleId: Long? = 0,

    var name: String?,

    var owner: User?,

    var description: String?,

    var condition: RuleCondition?

) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null)
}
