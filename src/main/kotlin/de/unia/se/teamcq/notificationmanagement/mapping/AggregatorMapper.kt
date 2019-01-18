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
class AggregatorMapper : IAggregatorMapper {

    @Autowired
    lateinit var aggregatorImmediateMapper: IAggregatorImmediateMapper

    @Autowired
    lateinit var aggregatorCountingMapper: IAggregatorCountingMapper

    @Autowired
    lateinit var aggregatorScheduledMapper: IAggregatorScheduledMapper

    override fun modelToEntity(aggregator: Aggregator): AggregatorEntity {
        return when (aggregator) {
            is AggregatorImmediate -> aggregatorImmediateMapper.modelToEntity(aggregator)
            is AggregatorCounting -> aggregatorCountingMapper.modelToEntity(aggregator)
            is AggregatorScheduled -> aggregatorScheduledMapper.modelToEntity(aggregator)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregator")
        }
    }

    override fun entityToModel(aggregatorEntity: AggregatorEntity): Aggregator {
        return when (aggregatorEntity) {
            is AggregatorImmediateEntity -> aggregatorImmediateMapper.entityToModel(aggregatorEntity)
            is AggregatorCountingEntity -> aggregatorCountingMapper.entityToModel(aggregatorEntity)
            is AggregatorScheduledEntity -> aggregatorScheduledMapper.entityToModel(aggregatorEntity)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregatorEntity")
        }
    }

    override fun modelToDto(aggregator: Aggregator): AggregatorDto {
        return when (aggregator) {
            is AggregatorImmediate -> aggregatorImmediateMapper.modelToDto(aggregator)
            is AggregatorCounting -> aggregatorCountingMapper.modelToDto(aggregator)
            is AggregatorScheduled -> aggregatorScheduledMapper.modelToDto(aggregator)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregator")
        }
    }

    override fun dtoToModel(aggregatorDto: AggregatorDto): Aggregator {
        return when (aggregatorDto) {
            is AggregatorImmediateDto -> aggregatorImmediateMapper.dtoToModel(aggregatorDto)
            is AggregatorCountingDto -> aggregatorCountingMapper.dtoToModel(aggregatorDto)
            is AggregatorScheduledDto -> aggregatorScheduledMapper.dtoToModel(aggregatorDto)
            else -> throw IllegalArgumentException("Unknown aggregator type: $aggregatorDto")
        }
    }
}
