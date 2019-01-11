package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.user.entity.UserEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.ManyToOne
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.validation.constraints.NotNull

@Entity
data class NotificationRuleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var ruleId: Long? = 0,

    @get: NotNull
    var name: String?,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_owner")
    var owner: UserEntity?,

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
