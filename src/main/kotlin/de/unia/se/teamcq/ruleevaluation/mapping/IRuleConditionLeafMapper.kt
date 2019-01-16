package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionLeafDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionLeafEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionLeaf
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [IRuleConditionPredicateMapper::class])
interface IRuleConditionLeafMapper {

    fun modelToDto(ruleConditionLeaf: RuleConditionLeaf):
            RuleConditionLeafDto

    fun dtoToModel(ruleConditionLeafDto: RuleConditionLeafDto):
            RuleConditionLeaf

    fun modelToEntity(ruleConditionLeaf: RuleConditionLeaf):
            RuleConditionLeafEntity

    fun entityToModel(ruleConditionLeafEntity: RuleConditionLeafEntity):
            RuleConditionLeaf
}
