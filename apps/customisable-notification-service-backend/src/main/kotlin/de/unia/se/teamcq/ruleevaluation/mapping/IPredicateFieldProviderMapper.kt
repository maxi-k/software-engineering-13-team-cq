package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldDto
import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldProviderDto
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", uses = [IPredicateFieldMapper::class])
interface IPredicateFieldProviderMapper {

    @Mappings(
            Mapping(source = "predicateFieldProviderName", target = "providerName"),
            Mapping(expression = "java(predicateFieldsToPredicateFieldDtos(predicateFieldProvider.predicateFieldValues()))",
                    target = "predicateFields")
    )
    fun modelToDto(predicateFieldProvider: IPredicateFieldProvider): PredicateFieldProviderDto

    fun predicateFieldsToPredicateFieldDtos(predicateFields: List<PredicateField>): List<PredicateFieldDto>
}
