package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorScheduledDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorScheduledEntity
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
import org.mapstruct.Mapper
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", uses = [CronTriggerMapper::class])
interface IAggregatorScheduledMapper {

    fun modelToDto(notificationAggregatorScheduled: AggregatorScheduled):
            AggregatorScheduledDto

    fun dtoToModel(notificationAggregatorScheduledDto: AggregatorScheduledDto):
            AggregatorScheduled

    fun modelToEntity(notificationAggregatorScheduled: AggregatorScheduled):
            AggregatorScheduledEntity

    fun entityToModel(NotificationAggregatorScheduledEntity: AggregatorScheduledEntity):
            AggregatorScheduled
}

@Component
object CronTriggerMapper {

    fun stringToCronTrigger(string: String): CronTrigger {
        return CronTrigger(string)
    }

    fun cronTriggerToString(cronTrigger: CronTrigger): String {
        return cronTrigger.expression
    }
}
