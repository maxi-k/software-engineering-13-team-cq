package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionPredicateDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionPredicateEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface IRuleConditionPredicateMapper {

    @BeforeMapping
    @Throws(IllegalArgumentException::class)
    fun checkLegalArguments(ruleConditionPredicateDto: RuleConditionPredicateDto) {

        if (ruleConditionPredicateDto.fieldName.isNullOrBlank()) {
            throw IllegalArgumentException(
                    "Attribute fieldName of RuleConditionPredicateDto" +
                    " is required, but was null or a blank string!"
            )
        }
        if (ruleConditionPredicateDto.providerName.isNullOrBlank()) {
            throw IllegalArgumentException(
                    "Attribute providerName of RuleConditionPredicateDto" +
                            " is required, but was null or a blank string!"
            )
        }
    }

    fun modelToDto(ruleConditionPredicate: RuleConditionPredicate):
            RuleConditionPredicateDto

    fun dtoToModel(notificationRuleConditionPredicateDto: RuleConditionPredicateDto):
            RuleConditionPredicate

    fun modelToEntity(ruleConditionPredicate: RuleConditionPredicate):
            RuleConditionPredicateEntity

    fun entityToModel(ruleConditionPredicateEntity: RuleConditionPredicateEntity):
            RuleConditionPredicate
}
