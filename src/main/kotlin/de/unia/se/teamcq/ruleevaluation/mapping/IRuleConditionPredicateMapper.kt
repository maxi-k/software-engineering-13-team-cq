package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionPredicateDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionPredicateEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface IRuleConditionPredicateMapper {

    fun modelToDto(ruleConditionPredicate: RuleConditionPredicate):
            RuleConditionPredicateDto

    fun dtoToModel(notificationRuleConditionPredicateDto: RuleConditionPredicateDto):
            RuleConditionPredicate

    fun modelToEntity(ruleConditionPredicate: RuleConditionPredicate):
            RuleConditionPredicateEntity

    fun entityToModel(ruleConditionPredicateEntity: RuleConditionPredicateEntity):
            RuleConditionPredicate
}
