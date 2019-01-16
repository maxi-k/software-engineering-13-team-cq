package de.unia.se.teamcq.user.dto

import java.io.Serializable

data class UserDto(

    var name: String?,
    var mailAddress: String? = null,
    var cellPhoneNumber: String? = null,
    var userSettings: UserSettingsDto?

) : Serializable {
    constructor(): this(null, null, null, null)
}
