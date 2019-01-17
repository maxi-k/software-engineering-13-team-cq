package de.unia.se.teamcq.notificationmanagement.entity

import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class NotificationAggregatorScheduledEntity(

    aggregatorId: Long? = 0,

    @get: NotNull
    var notificationCronTrigger: String?

) : NotificationAggregatorEntity(aggregatorId) {

    // Necessary for MapStruct
    constructor() : this(null, null)

    // Autogenerated by IntelliJ
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (notificationCronTrigger?.hashCode() ?: 0)
        return result
    }

    // Autogenerated by IntelliJ
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NotificationAggregatorScheduledEntity) return false
        if (!super.equals(other)) return false

        if (notificationCronTrigger != other.notificationCronTrigger) return false

        return true
    }
}
