package de.unia.se.teamcq.user.dto

import de.unia.se.teamcq.user.model.UserLocale
import de.unia.se.teamcq.user.model.UserNotificationType
import java.io.Serializable

// Constructor with (null)-default values for everything necessary for MapStruct
data class UserSettingsDto(

    var settingsId: Long? = null,

    var userNotificationType: UserNotificationType? = null,

    var locale: UserLocale? = null

) : Serializable
