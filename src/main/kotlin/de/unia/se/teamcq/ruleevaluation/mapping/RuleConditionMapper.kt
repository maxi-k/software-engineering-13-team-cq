package de.unia.se.teamcq.ruleevaluation.mapping

import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionCompositeDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionPredicateDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionCompositeEntity
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionEntity
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionPredicateEntity
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RuleConditionMapper : IRuleConditionMapper {

    @Autowired
    lateinit var ruleConditionPredicateMapper: IRuleConditionPredicateMapper

    override fun modelToDto(ruleConditionModel: RuleCondition): RuleConditionDto {

        return if (ruleConditionModel is RuleConditionPredicate) {
            ruleConditionPredicateMapper.modelToDto(ruleConditionModel)
        } else {

            val ruleConditionCompositeModel = ruleConditionModel as RuleConditionComposite

            val ruleConditionDto = RuleConditionCompositeDto()
            ruleConditionDto.conditionId = ruleConditionCompositeModel.conditionId
            ruleConditionDto.logicalConnective = ruleConditionCompositeModel.logicalConnective
            ruleConditionDto.subConditions = ruleConditionCompositeModel.subConditions.map { condition ->
                modelToDto(condition)
            }

            ruleConditionDto
        }
    }

    override fun dtoToModel(ruleConditionDto: RuleConditionDto): RuleCondition {
        return if (ruleConditionDto is RuleConditionPredicateDto) {
            ruleConditionPredicateMapper.dtoToModel(ruleConditionDto)
        } else {

            val ruleConditionCompositeDto = ruleConditionDto as RuleConditionCompositeDto

            val ruleCondition = RuleConditionComposite()
            ruleCondition.conditionId = ruleConditionCompositeDto.conditionId
            ruleCondition.logicalConnective = ruleConditionCompositeDto.logicalConnective
            ruleCondition.subConditions = ruleConditionCompositeDto.subConditions.map { condition ->
                dtoToModel(condition)
            }

            ruleCondition
        }
    }

    override fun modelToEntity(ruleConditionModel: RuleCondition): RuleConditionEntity {

        return if (ruleConditionModel is RuleConditionPredicate) {
            ruleConditionPredicateMapper.modelToEntity(ruleConditionModel)
        } else {

            val ruleConditionCompositeModel = ruleConditionModel as RuleConditionComposite

            val ruleConditionEntity = RuleConditionCompositeEntity()
            ruleConditionEntity.conditionId = ruleConditionCompositeModel.conditionId
            ruleConditionEntity.logicalConnective = ruleConditionCompositeModel.logicalConnective
            ruleConditionEntity.subConditions = ruleConditionCompositeModel.subConditions.map { condition ->
                modelToEntity(condition)
            }

            ruleConditionEntity
        }
    }

    override fun entityToModel(ruleConditionEntity: RuleConditionEntity): RuleCondition {

        return if (ruleConditionEntity is RuleConditionPredicateEntity) {
            ruleConditionPredicateMapper.entityToModel(ruleConditionEntity)
        } else {

            val ruleConditionCompositeEntity = ruleConditionEntity as RuleConditionCompositeEntity

            val ruleCondition = RuleConditionComposite()
            ruleCondition.conditionId = ruleConditionCompositeEntity.conditionId
            ruleCondition.logicalConnective = ruleConditionCompositeEntity.logicalConnective
            ruleCondition.subConditions = ruleConditionCompositeEntity.subConditions.map { condition ->
                entityToModel(condition)
            }

            ruleCondition
        }
    }
}
