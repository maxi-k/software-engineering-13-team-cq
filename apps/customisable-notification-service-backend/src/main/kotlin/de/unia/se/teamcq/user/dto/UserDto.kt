package de.unia.se.teamcq.user.dto

import java.io.Serializable

// Constructor with (null)-default values for everything necessary for MapStruct
data class UserDto(

    var name: String? = null,
    var mailAddress: String? = null,
    var cellPhoneNumber: String? = null,
    var userSettings: UserSettingsDto? = null

) : Serializable
