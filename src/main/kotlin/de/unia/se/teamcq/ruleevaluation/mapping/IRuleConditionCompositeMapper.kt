package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionCompositeDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionCompositeEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [IRuleConditionPredicateMapper::class])
interface IRuleConditionCompositeMapper {

    fun modelToDto(ruleConditionComposite: RuleConditionComposite):
            RuleConditionCompositeDto

    fun dtoToModel(ruleConditionCompositeDto: RuleConditionCompositeDto):
            RuleConditionComposite

    fun modelToEntity(ruleConditionComposite: RuleConditionComposite):
            RuleConditionCompositeEntity

    fun entityToModel(ruleConditionCompositeEntity: RuleConditionCompositeEntity):
            RuleConditionComposite
}
