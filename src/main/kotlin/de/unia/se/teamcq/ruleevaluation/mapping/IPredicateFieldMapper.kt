package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldProviderDto
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IPredicateFieldMapper {

    fun modelToDto(predicateFieldProvider: IPredicateFieldProvider): PredicateFieldProviderDto
}
