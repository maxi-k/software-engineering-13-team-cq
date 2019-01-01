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
    val name: String,

    @get: NotNull
    val recipients: MutableList<String>,

    val description: String,

    // @get: NotNull
    // val fleets: MutableList<Fleet>,

    // @get: NotNull
    // val formula: Formula,

    val notificationData: NotificationData?,

    @get: NotNull
    val aggregator: Aggregator,

    @get: NotNull
    val user: User

) : Serializable
