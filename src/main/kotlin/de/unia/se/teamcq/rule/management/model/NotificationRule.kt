package de.unia.se.teamcq.rule.management.model

import de.unia.se.teamcq.user.management.User
import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GenerationType
import javax.validation.constraints.NotNull
import de.unia.se.teamcq.notification.management.Aggregator
import de.unia.se.teamcq.notification.management.NotificationData

@Entity
data class NotificationRule(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @get: NotNull
    var name: String,

    @get: NotNull
    var recipients: MutableList<String>,

    var description: String,

    // @get: NotNull
    // var fleets: MutableList<Fleet>,

    // @get: NotNull
    // var formula: Formula,

    var notificationData: NotificationData?,

    @get: NotNull
    var aggregator: Aggregator,

    @get: NotNull
    var user: User

) : Serializable
