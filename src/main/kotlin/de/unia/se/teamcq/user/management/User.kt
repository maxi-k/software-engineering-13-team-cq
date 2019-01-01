package de.unia.se.teamcq.user.management

import java.io.Serializable

data class User (

        val mailAddress: String?,
        val cellPhoneNumber: String?
        //val userSettings: UserSettings

) : Serializable
