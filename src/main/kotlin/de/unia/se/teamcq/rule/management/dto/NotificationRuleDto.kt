package de.unia.se.teamcq.rule.management.dto

import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

data class NotificationRuleDto(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0,

    @get: NotNull
    var name: String?,

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
    constructor() : this(null, null, null)
}
