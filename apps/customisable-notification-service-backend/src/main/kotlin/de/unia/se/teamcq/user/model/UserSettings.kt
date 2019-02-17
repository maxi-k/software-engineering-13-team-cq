package de.unia.se.teamcq.user.model

// Constructor with (null)-default values for everything necessary for MapStruct
data class UserSettings(

    var settingsId: Long? = null,

    var userNotificationType: UserNotificationType? = null,

    var locale: UserLocale? = null
) {

    companion object {
        val DEFAULT_USER_SETTINGS = UserSettings(null, UserNotificationType.EMAIL, UserLocale.EN)
    }
}
