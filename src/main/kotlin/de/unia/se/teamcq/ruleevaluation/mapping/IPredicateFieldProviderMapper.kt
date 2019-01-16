package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldProviderDto
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [IPredicateFieldMapper::class])
interface IPredicateFieldProviderMapper {

    @Mapping(source = "predicateFieldProviderName", target = "name")
    fun modelToDto(predicateFieldProvider: IPredicateFieldProvider): PredicateFieldProviderDto
}
