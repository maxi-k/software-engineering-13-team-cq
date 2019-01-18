package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorEntity
import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import org.springframework.stereotype.Service

@Service
interface INotificationAggregatorMapper {

    fun modelToEntity(aggregator: Aggregator): AggregatorEntity

    fun entityToModel(aggregatorEntity: AggregatorEntity): Aggregator

    fun modelToDto(aggregator: Aggregator): AggregatorDto

    fun dtoToModel(aggregatorDto: AggregatorDto): Aggregator
}
