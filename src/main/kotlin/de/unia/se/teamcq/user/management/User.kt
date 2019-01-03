package de.unia.se.teamcq.user.management

import java.io.Serializable

data class User(

    var mailAddress: String? = null,
    var cellPhoneNumber: String? = null,
    var userSettings: UserSettings

) : Serializable
