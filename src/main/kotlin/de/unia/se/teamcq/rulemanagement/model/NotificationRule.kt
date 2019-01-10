package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.user.model.User

data class NotificationRule(
    var id: Long? = 0,

    var name: String?,

    var owner: User?,

    // @get: NotNull
    // var recipients: List<String>,

    var description: String?

    // @get: NotNull
    // var fleets: MutableList<Fleet>,

    // @get: NotNull
    // var formula: Formula,

    // @get: NotNull
    // var aggregator: Aggregator,

) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
