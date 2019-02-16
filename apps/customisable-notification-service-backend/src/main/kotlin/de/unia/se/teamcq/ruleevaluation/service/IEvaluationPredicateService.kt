package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.stereotype.Service

/**
 * A service to evaluate [RuleConditionPredicate]s.
 */
@Service
interface IEvaluationPredicateService {

    /**
     * Evaluate a [RuleConditionPredicate] on a [PredicateField] with dataType [VehicleStateDataType]
     *
     * @param ruleConditionPredicate Containing the [ComparisonType] and the comparisonValue
     * @param vehicleStateDataType The [VehicleStateDataType] containing the value for [PredicateField]
     * @param predicateField The [PredicateField] with the name of the field
     * @returns The evaluated [RuleConditionPredicate]
     */
    fun checkPredicate(
        ruleConditionPredicate: RuleConditionPredicate,
        vehicleStateDataType: VehicleStateDataType,
        predicateField: PredicateField
    ): Boolean
}
