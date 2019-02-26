package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionPredicateDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionPredicateEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class AbstractRuleConditionPredicateMapper {

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

        if (ruleConditionPredicateDto.comparisonType == null) {
            throw IllegalArgumentException(
                    "Attribute comparisonType of RuleConditionPredicateDto" +
                            " is required, but was null!"
            )
        }

        if (ruleConditionPredicateDto.comparisonValue.isNullOrBlank()) {
            throw IllegalArgumentException(
                    "Attribute comparisonType of RuleConditionPredicateDto" +
                            " is required, but was null or a blank string!"
            )
        }
    }

    abstract fun modelToDto(ruleConditionPredicate: RuleConditionPredicate):
            RuleConditionPredicateDto

    abstract fun dtoToModel(notificationRuleConditionPredicateDto: RuleConditionPredicateDto):
            RuleConditionPredicate

    abstract fun modelToEntity(ruleConditionPredicate: RuleConditionPredicate):
            RuleConditionPredicateEntity

    abstract fun entityToModel(ruleConditionPredicateEntity: RuleConditionPredicateEntity):
            RuleConditionPredicate
}
