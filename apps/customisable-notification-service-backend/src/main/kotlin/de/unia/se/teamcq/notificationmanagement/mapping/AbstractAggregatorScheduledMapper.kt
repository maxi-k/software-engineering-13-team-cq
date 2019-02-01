package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorScheduledDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorScheduledEntity
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", uses = [CronTriggerMapper::class])
abstract class AbstractAggregatorScheduledMapper {

    @BeforeMapping
    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(aggregatorScheduledDto: AggregatorScheduledDto) {

        if (aggregatorScheduledDto.notificationCronTrigger == null) {
            throw IllegalArgumentException("Attribute NotificationCronTrigger of AggregatorScheduledDto" +
                    " is required but was null!")
        } else {
            try {
                CronTrigger(aggregatorScheduledDto.notificationCronTrigger!!)
            } catch (illegalArgumentException: IllegalArgumentException) {
                throw IllegalArgumentException("Attribute NotificationCronTrigger of AggregatorScheduledDto" +
                " must be a valid CronTrigger but was ${aggregatorScheduledDto.notificationCronTrigger!!}!")
            }
        }
    }

    abstract fun modelToDto(aggregatorScheduled: AggregatorScheduled):
            AggregatorScheduledDto

    abstract fun dtoToModel(aggregatorScheduledDto: AggregatorScheduledDto):
            AggregatorScheduled

    abstract fun modelToEntity(aggregatorScheduled: AggregatorScheduled):
            AggregatorScheduledEntity

    abstract fun entityToModel(aggregatorScheduledEntity: AggregatorScheduledEntity):
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
