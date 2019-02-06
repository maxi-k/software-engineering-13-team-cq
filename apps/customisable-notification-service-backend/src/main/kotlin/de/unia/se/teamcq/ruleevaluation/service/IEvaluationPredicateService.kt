package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.stereotype.Service

@Service
interface IEvaluationPredicateService {

    fun <T : VehicleStateDataType> checkPredicate(ruleConditionPredicate: RuleConditionPredicate, vehicleStateDataType: T, predicateField: PredicateField<T, Any>): Boolean
}
