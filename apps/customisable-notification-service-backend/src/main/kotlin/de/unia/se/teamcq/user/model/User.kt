package de.unia.se.teamcq.user.model

// Constructor with (null)-default values for everything necessary for MapStruct
data class User(

    var name: String? = null,
    var mailAddress: String? = null,
    var cellPhoneNumber: String? = null,
    var userSettings: UserSettings? = null

)
