package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorCountingDto
import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorDto
import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorImmediateDto
import de.unia.se.teamcq.notificationmanagement.dto.NotificationAggregatorScheduledDto
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorCountingEntity
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorEntity
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorImmediateEntity
import de.unia.se.teamcq.notificationmanagement.entity.NotificationAggregatorScheduledEntity
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregator
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregatorCounting
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregatorImmediate
import de.unia.se.teamcq.notificationmanagement.model.NotificationAggregatorScheduled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Autowired
lateinit var notificationAggregatorImmediatetlyMapper: INotificationAggregatorImmediatetlyMapper

@Autowired
lateinit var notificationAggregatorCountingMapper: INotificationAggregatorCountingMapper

@Autowired
lateinit var notificationAggregatorScheduledMapper: INotificationAggregatorScheduledMapper

@Component
class NotificationAggregatorMapper : INotificationAggregatorMapper {

    override fun modelToEntity(notificationAggregator: NotificationAggregator): NotificationAggregatorEntity {
        return when (notificationAggregator) {
            is NotificationAggregatorImmediate -> notificationAggregatorImmediatetlyMapper.modelToEntity(notificationAggregator)
            is NotificationAggregatorCounting -> notificationAggregatorCountingMapper.modelToEntity(notificationAggregator)
            is NotificationAggregatorScheduled -> notificationAggregatorScheduledMapper.modelToEntity(notificationAggregator)
            else -> throw IllegalArgumentException("Unknown aggregator type: $notificationAggregator")
        }
    }

    override fun entityToModel(notificationAggregatorEntity: NotificationAggregatorEntity): NotificationAggregator {
        return when (notificationAggregatorEntity) {
            is NotificationAggregatorImmediateEntity -> notificationAggregatorImmediatetlyMapper.entityToModel(notificationAggregatorEntity)
            is NotificationAggregatorCountingEntity -> notificationAggregatorCountingMapper.entityToModel(notificationAggregatorEntity)
            is NotificationAggregatorScheduledEntity -> notificationAggregatorScheduledMapper.entityToModel(notificationAggregatorEntity)
            else -> throw IllegalArgumentException("Unknown aggregator type: $notificationAggregatorEntity")
        }
    }

    override fun modelToDto(notificationAggregator: NotificationAggregator): NotificationAggregatorDto {
        return when (notificationAggregator) {
            is NotificationAggregatorImmediate -> notificationAggregatorImmediatetlyMapper.modelToDto(notificationAggregator)
            is NotificationAggregatorCounting -> notificationAggregatorCountingMapper.modelToDto(notificationAggregator)
            is NotificationAggregatorScheduled -> notificationAggregatorScheduledMapper.modelToDto(notificationAggregator)
            else -> throw IllegalArgumentException("Unknown aggregator type: $notificationAggregator")
        }
    }

    override fun dtoToModel(notificationAggregatorDto: NotificationAggregatorDto): NotificationAggregator {
        return when (notificationAggregatorDto) {
            is NotificationAggregatorImmediateDto -> notificationAggregatorImmediatetlyMapper.dtoToModel(notificationAggregatorDto)
            is NotificationAggregatorCountingDto -> notificationAggregatorCountingMapper.dtoToModel(notificationAggregatorDto)
            is NotificationAggregatorScheduledDto -> notificationAggregatorScheduledMapper.dtoToModel(notificationAggregatorDto)
            else -> throw IllegalArgumentException("Unknown aggregator type: $notificationAggregatorDto")
        }
    }
}
