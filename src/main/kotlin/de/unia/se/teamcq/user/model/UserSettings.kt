package de.unia.se.teamcq.user.model

data class UserSettings(

    var settingsId: Long?,

    var userNotificationType: UserNotificationType?
) {
    constructor(): this(null, null)
}
