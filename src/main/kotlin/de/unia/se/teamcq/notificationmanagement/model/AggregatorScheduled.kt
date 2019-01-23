package de.unia.se.teamcq.notificationmanagement.model

import org.springframework.scheduling.support.CronTrigger

class AggregatorScheduled(

    aggregatorId: Long? = 0,

    var notificationCronTrigger: CronTrigger?

) : Aggregator(aggregatorId) {

    // Necessary for MapStruct
    constructor() : this(null, null)

    // Autogenerated
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (notificationCronTrigger?.hashCode() ?: 0)
        return result
    }

    // Autogenerated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AggregatorScheduled) return false
        if (!super.equals(other)) return false

        if (notificationCronTrigger != other.notificationCronTrigger) return false

        return true
    }
}
