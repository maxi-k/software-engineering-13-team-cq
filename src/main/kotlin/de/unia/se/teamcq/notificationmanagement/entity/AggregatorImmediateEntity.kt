package de.unia.se.teamcq.notificationmanagement.entity

import javax.persistence.Entity

@Entity
class AggregatorImmediateEntity(

    aggregatorId: Long? = 0

) : AggregatorEntity(aggregatorId) {

    // Autogenerated by IntelliJ
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AggregatorImmediateEntity) return false
        if (!super.equals(other)) return false
        return true
    }
}
