package de.unia.se.teamcq.notificationmanagement.dto

class RecipientSmsDto(

        recipientId: Long? = 0,

        var phoneNumber: String?

) : RecipientDto(recipientId) {

    // Necessary for MapStruct
    constructor() : this(null, null)

    // Autogenerated by IntelliJ
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        return result
    }

    // Autogenerated by IntelliJ
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RecipientSmsDto) return false
        if (!super.equals(other)) return false

        if (phoneNumber != other.phoneNumber) return false

        return true
    }
}
