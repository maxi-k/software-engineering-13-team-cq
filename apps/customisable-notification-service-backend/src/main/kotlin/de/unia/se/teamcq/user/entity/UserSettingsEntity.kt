package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.user.model.UserLocale
import de.unia.se.teamcq.user.model.UserNotificationType
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
// Constructor with (null)-default values for everything necessary for MapStruct
data class UserSettingsEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var settingsId: Long? = null,

    var userNotificationType: UserNotificationType? = null,

    var locale: UserLocale? = null

) : Serializable
