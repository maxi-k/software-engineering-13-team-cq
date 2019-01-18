package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorImmediateDto
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorImmediateEntity
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregatorImmediate
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface INotificationAggregatorImmediatetlyMapper {

    fun modelToDto(notificationAggregatorImmediate: NotificationAggregatorImmediate):
            NotificationAggregatorImmediateDto

    fun dtoToModel(notificationAggregatorImmediateDto: NotificationAggregatorImmediateDto):
            NotificationAggregatorImmediate

    fun modelToEntity(notificationAggregatorImmediate: NotificationAggregatorImmediate):
            NotificationAggregatorImmediateEntity

    fun entityToModel(notificationAggregatorImmediateEntity: NotificationAggregatorImmediateEntity):
            NotificationAggregatorImmediate
}
