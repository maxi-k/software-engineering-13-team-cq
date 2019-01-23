package de.unia.se.teamcq.rulemanagement.dto

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionDto
import de.unia.se.teamcq.user.dto.UserDto
import java.io.Serializable

data class NotificationRuleDto(

    var ruleId: Long? = 0,

    var name: String?,

    var owner: UserDto?,

    var description: String?,

    var condition: RuleConditionDto?,

    var aggregator: AggregatorDto?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null, null)
}
