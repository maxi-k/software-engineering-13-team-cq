package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldDto
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IPredicateFieldMapper {

    fun modelToDto(predicateField: PredicateField): PredicateFieldDto
}
