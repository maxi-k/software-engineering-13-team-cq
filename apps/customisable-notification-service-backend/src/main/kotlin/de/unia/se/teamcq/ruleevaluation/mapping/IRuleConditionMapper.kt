package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import org.springframework.stereotype.Service

@Service
interface IRuleConditionMapper {

    fun modelToDto(ruleConditionModel: RuleCondition): RuleConditionDto

    fun dtoToModel(ruleConditionDto: RuleConditionDto): RuleCondition

    fun modelToEntity(ruleConditionModel: RuleCondition): RuleConditionEntity

    fun entityToModel(ruleConditionEntity: RuleConditionEntity): RuleCondition
}
