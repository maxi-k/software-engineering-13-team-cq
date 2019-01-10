package de.unia.se.teamcq.rulemanagement.dto

import de.unia.se.teamcq.user.dto.UserDto
import java.io.Serializable

data class NotificationRuleDto(
    var id: Long? = 0,

    var name: String?,

    var owner: UserDto?,

    // @get: NotNull
    // var recipients: List<String>,

    var description: String?

    // @get: NotNull
    // var fleets: MutableList<Fleet>,

    // @get: NotNull
    // var formula: Formula,

    // var notificationData: NotificationData?,

    // @get: NotNull
    // var aggregator: Aggregator,

    // @get: NotNull
    // var user: User

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
