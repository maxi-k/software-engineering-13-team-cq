package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import org.springframework.stereotype.Component

@Component
class RuleConditionMapper : IRuleConditionMapper {

    override fun modelToDto(ruleCondition: RuleCondition): RuleConditionDto {
        TODO()
    }

    override fun dtoToModel(ruleConditionDto: RuleConditionDto): RuleCondition {
        TODO()
    }

    override fun modelToEntity(ruleCondition: RuleCondition): RuleConditionEntity {
        TODO()
    }

    override fun entityToModel(ruleConditionEntity: RuleConditionEntity): RuleCondition {
        TODO()
    }
}
