package de.unia.se.teamcq.rule.management.model

// import de.unia.se.teamcq.notification.management.Aggregator

data class NotificationRule(
    var id: Long? = 0,

    var name: String?,

    // @get: NotNull
    // var recipients: List<String>,

    var description: String?

    // @get: NotNull
    // var fleets: MutableList<Fleet>,

    // @get: NotNull
    // var formula: Formula,

    // @get: NotNull
    // var aggregator: Aggregator,

    // @get: NotNull
    // var creator: User

) {
    // Necessary for MapStruct
    constructor() : this(null, null, null)
}
