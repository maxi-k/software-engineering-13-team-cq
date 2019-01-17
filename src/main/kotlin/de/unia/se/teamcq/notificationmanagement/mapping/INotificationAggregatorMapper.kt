package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorDto
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorEntity
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregator
import org.springframework.stereotype.Service

@Service
interface INotificationAggregatorMapper {

    fun modelToEntity(notificationAggregator: NotificationAggregator): NotificationAggregatorEntity

    fun entityToModel(notificationAggregatorEntity: NotificationAggregatorEntity): NotificationAggregator

    fun modelToDto(notificationAggregator: NotificationAggregator): NotificationAggregatorDto

    fun dtoToModel(notificationAggregatorDto: NotificationAggregatorDto): NotificationAggregator
}
