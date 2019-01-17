package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorScheduledDto
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorScheduledEntity
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregatorScheduled
import org.mapstruct.Mapper
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", uses = [CronTriggerMapper::class])
interface INotificationAggregatorScheduledMapper {

    fun modelToDto(notificationAggregatorScheduled: NotificationAggregatorScheduled):
            NotificationAggregatorScheduledDto

    fun dtoToModel(notificationAggregatorScheduledDto: NotificationAggregatorScheduledDto):
            NotificationAggregatorScheduled

    fun modelToEntity(notificationAggregatorScheduled: NotificationAggregatorScheduled):
            NotificationAggregatorScheduledEntity

    fun entityToModel(NotificationAggregatorScheduledEntity: NotificationAggregatorScheduledEntity):
            NotificationAggregatorScheduled
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
