package de.unia.se.teamcq.notificationmanagement.model

class NotificationAggregatorImmediate(

    aggregatorId: Long? = 0

) : NotificationAggregator(aggregatorId) {

    // Autogenerated by IntelliJ
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NotificationAggregatorImmediate) return false
        if (!super.equals(other)) return false
        return true
    }
}