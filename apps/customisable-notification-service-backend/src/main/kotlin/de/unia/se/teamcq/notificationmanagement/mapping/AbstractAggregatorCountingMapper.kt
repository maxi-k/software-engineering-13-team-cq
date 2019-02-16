package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorCountingDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorCountingEntity
import de.unia.se.teamcq.notificationmanagement.model.AggregatorCounting
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class AbstractAggregatorCountingMapper {

    @BeforeMapping
    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(aggregatorCountingDto: AggregatorCountingDto) {

        if (aggregatorCountingDto.notificationCountThreshold == null) {
            throw IllegalArgumentException("Attribute NotificationCountThreshold of AggregatorCountingDto" +
                    " is required but was null!")
        } else if (aggregatorCountingDto.notificationCountThreshold!! < 0) {
            throw IllegalArgumentException("Attribute NotificationCountThreshold of AggregatorCountingDto " +
                    " has to be above 0 but was ${aggregatorCountingDto.notificationCountThreshold}")
        }
    }

    @Throws(IllegalArgumentException::class)
    abstract fun modelToDto(notificationAggregatorCounting: AggregatorCounting):
            AggregatorCountingDto

    abstract fun dtoToModel(notificationAggregatorCountingDto: AggregatorCountingDto):
            AggregatorCounting

    abstract fun modelToEntity(notificationAggregatorCounting: AggregatorCounting):
            AggregatorCountingEntity

    abstract fun entityToModel(aggregatorCountingEntity: AggregatorCountingEntity):
            AggregatorCounting
}
