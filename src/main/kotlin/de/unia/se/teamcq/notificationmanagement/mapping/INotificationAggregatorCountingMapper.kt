package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorCountingDto
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorCountingEntity
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregatorCounting
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface INotificationAggregatorCountingMapper {

    fun modelToDto(notificationAggregatorCounting: NotificationAggregatorCounting):
            NotificationAggregatorCountingDto

    fun dtoToModel(notificationAggregatorCountingDto: NotificationAggregatorCountingDto):
            NotificationAggregatorCounting

    fun modelToEntity(notificationAggregatorCounting: NotificationAggregatorCounting):
            NotificationAggregatorCountingEntity

    fun entityToModel(notificationAggregatorCountingEntity: NotificationAggregatorCountingEntity):
            NotificationAggregatorCounting
}
