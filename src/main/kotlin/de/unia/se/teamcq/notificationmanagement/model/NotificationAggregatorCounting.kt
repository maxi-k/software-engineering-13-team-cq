package de.unia.se.teamcq.notificationmanagement.model

class NotificationAggregatorCounting(

    aggregatorId: Long? = 0,

    var notificationCountThreshold: Int?

) : NotificationAggregator(aggregatorId) {

    // Necessary for MapStruct
    constructor() : this(null, null)

    // Autogenerated by IntelliJ
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (notificationCountThreshold ?: 0)
        return result
    }

    // Autogenerated by IntelliJ
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NotificationAggregatorCounting) return false
        if (!super.equals(other)) return false

        if (notificationCountThreshold != other.notificationCountThreshold) return false

        return true
    }
}