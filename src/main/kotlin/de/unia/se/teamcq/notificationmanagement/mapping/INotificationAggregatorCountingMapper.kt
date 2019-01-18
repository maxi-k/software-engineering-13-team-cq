package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorCountingDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorCountingEntity
import de.unia.se.teamcq.notificationmanagement.model.AggregatorCounting
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface INotificationAggregatorCountingMapper {

    fun modelToDto(notificationAggregatorCounting: AggregatorCounting):
            AggregatorCountingDto

    fun dtoToModel(notificationAggregatorCountingDto: AggregatorCountingDto):
            AggregatorCounting

    fun modelToEntity(notificationAggregatorCounting: AggregatorCounting):
            AggregatorCountingEntity

    fun entityToModel(aggregatorCountingEntity: AggregatorCountingEntity):
            AggregatorCounting
}
