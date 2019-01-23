package de.unia.se.teamcq.notificationmanagement.model

class RecipientSms(

    recipientId: Long? = 0,

    var phoneNumber: String?

) : Recipient(recipientId) {

    // Necessary for MapStruct
    constructor() : this(null, null)

    // Autogenerated
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        return result
    }

    // Autogenerated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RecipientSms) return false
        if (!super.equals(other)) return false

        if (phoneNumber != other.phoneNumber) return false

        return true
    }
}
