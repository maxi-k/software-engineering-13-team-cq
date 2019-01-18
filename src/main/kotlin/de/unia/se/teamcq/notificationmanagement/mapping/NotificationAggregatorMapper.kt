package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorCountingDto
import de.unia.se.teamcq.notificationmanagement.dto.AggregatorDto
import de.unia.se.teamcq.notificationmanagement.dto.AggregatorImmediateDto
import de.unia.se.teamcq.notificationmanagement.dto.AggregatorScheduledDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorCountingEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorImmediateEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorScheduledEntity
import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.AggregatorCounting
import de.unia.se.teamcq.notificationmanagement.model.AggregatorImmediate
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NotificationAggregatorMapper : INotificationAggregatorMapper {

    @Autowired
    lateinit var notificationAggregatorImmediatetlyMapper: INotificationAggregatorImmediatetlyMapper

    @Autowired
    lateinit var notificationAggregatorCountingMapper: INotificationAggregatorCountingMapper

    @Autowired
    lateinit var notificationAggregatorScheduledMapper: INotificationAggregatorScheduledMapper

    override fun modelToEntity(aggregator: Aggregator): AggregatorEntity {
        return when (aggregator) {
            is AggregatorImmediate -> notificationAggregatorImmediatetlyMapper.modelToEntity(aggregator)
            is AggregatorCounting -> notificationAggregatorCountingMapper.modelToEntity(aggregator)
            is AggregatorScheduled -> notificationAggregatorScheduledMapper.modelToEntity(aggregator)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregator")
        }
    }

    override fun entityToModel(aggregatorEntity: AggregatorEntity): Aggregator {
        return when (aggregatorEntity) {
            is AggregatorImmediateEntity -> notificationAggregatorImmediatetlyMapper.entityToModel(aggregatorEntity)
            is AggregatorCountingEntity -> notificationAggregatorCountingMapper.entityToModel(aggregatorEntity)
            is AggregatorScheduledEntity -> notificationAggregatorScheduledMapper.entityToModel(aggregatorEntity)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregatorEntity")
        }
    }

    override fun modelToDto(aggregator: Aggregator): AggregatorDto {
        return when (aggregator) {
            is AggregatorImmediate -> notificationAggregatorImmediatetlyMapper.modelToDto(aggregator)
            is AggregatorCounting -> notificationAggregatorCountingMapper.modelToDto(aggregator)
            is AggregatorScheduled -> notificationAggregatorScheduledMapper.modelToDto(aggregator)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregator")
        }
    }

    override fun dtoToModel(aggregatorDto: AggregatorDto): Aggregator {
        return when (aggregatorDto) {
            is AggregatorImmediateDto -> notificationAggregatorImmediatetlyMapper.dtoToModel(aggregatorDto)
            is AggregatorCountingDto -> notificationAggregatorCountingMapper.dtoToModel(aggregatorDto)
            is AggregatorScheduledDto -> notificationAggregatorScheduledMapper.dtoToModel(aggregatorDto)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregatorDto")
        }
    }
}
