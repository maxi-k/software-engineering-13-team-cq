package de.unia.se.teamcq.events.model

import javax.validation.constraints.NotEmpty
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class VehicleStatus(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @get: NotEmpty
    val title: String = "",

    @get: NotEmpty
    val content: String = ""
) : Serializable