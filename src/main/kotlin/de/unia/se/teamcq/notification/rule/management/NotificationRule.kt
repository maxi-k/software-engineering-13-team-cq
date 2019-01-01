package de.unia.se.teamcq.notification.rule.management

import java.io.Serializable
import java.util.concurrent.locks.Condition
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
data class NotificationRule (
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,

        @get: NotNull
        val name: String,

        @get: NotNull
        val recipients: MutableList<String>,

        val description: String,

        //@get: NotNull
        //val fleets: MutableList<Fleet>,

        //@get: NotNull
        //val formula: Formula,

        //@get: NotNull
        //val aggregator: Aggregator,

        //@get: NotNull
        //val user: User


) : Serializable
