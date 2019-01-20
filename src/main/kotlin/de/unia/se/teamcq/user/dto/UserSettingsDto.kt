package de.unia.se.teamcq.user.dto

import de.unia.se.teamcq.user.model.UserNotificationType
import java.io.Serializable

data class UserSettingsDto(

    var settingsId: Long?,

    var userNotificationType: UserNotificationType?

) : Serializable {
    constructor(): this(null, null)
}
