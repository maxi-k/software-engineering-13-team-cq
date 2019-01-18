package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorImmediateDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorImmediateEntity
import de.unia.se.teamcq.notificationmanagement.model.AggregatorImmediate
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface INotificationAggregatorImmediatetlyMapper {

    fun modelToDto(notificationAggregatorImmediate: AggregatorImmediate):
            AggregatorImmediateDto

    fun dtoToModel(notificationAggregatorImmediateDto: AggregatorImmediateDto):
            AggregatorImmediate

    fun modelToEntity(notificationAggregatorImmediate: AggregatorImmediate):
            AggregatorImmediateEntity

    fun entityToModel(notificationAggregatorImmediateEntity: AggregatorImmediateEntity):
            AggregatorImmediate
}
