package de.unia.se.teamcq.user.model

data class User(

    var name: String?,
    var mailAddress: String?,
    var cellPhoneNumber: String?,
    var userSettings: UserSettings?

) {
    constructor(): this(null, null, null, null)
}
