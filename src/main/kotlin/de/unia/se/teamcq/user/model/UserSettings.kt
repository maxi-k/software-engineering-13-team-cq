package de.unia.se.teamcq.user.model

data class UserSettings(
    var userNotificationType: UserNotificationType?
) {
    constructor(): this(null)
}
