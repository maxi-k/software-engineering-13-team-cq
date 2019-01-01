package de.unia.se.teamcq.user.management

import java.io.Serializable

data class User(

    var mailAddress: String?,
    var cellPhoneNumber: String?,
    var userSettings: UserSettings

) : Serializable
