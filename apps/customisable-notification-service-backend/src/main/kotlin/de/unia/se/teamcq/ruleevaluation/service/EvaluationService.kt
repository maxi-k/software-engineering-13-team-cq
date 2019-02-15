package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EvaluationService : IEvaluationService {

    @Autowired
    lateinit var predicateFieldContainer: IPredicateFieldContainer

    @Autowired
    lateinit var evaluationPredicateService: IEvaluationPredicateService

    @Throws(IllegalArgumentException::class)
    override fun checkCondition(ruleCondition: RuleCondition, vehicleState: VehicleState): Boolean {
        when (ruleCondition) {
            is RuleConditionComposite -> {
                ruleCondition.logicalConnective?.getPredicateReducer()?.let { predicateReducer ->
                    return predicateReducer(ruleCondition.subConditions) { subCondition ->
                        // TODO: Catch recursive call exceptions here, and decide whether to
                        // throw or return? Use custom Exceptions, instead of IllegalArgumentExceptions
                        // to decide?
                        checkCondition(subCondition, vehicleState)
                    }
                }
                throw IllegalArgumentException("Given composite NotificationRule did not have a logical connective.")
            }
            is RuleConditionPredicate -> {
                // If the condition did not supply a provider and a target field,
                // throw an exception. Don't return false, as a ConditionComposite with Connective NONE
                // could apply, even though the condition didn't even apply to a field
                if (ruleCondition.providerName.isNullOrBlank() || ruleCondition.fieldName.isNullOrBlank()) {
                    throw IllegalArgumentException("Given predicate NotificationRule did not specify a target PredicateField")
                }
                // Find the PredicateField this Condition should use,
                // throw an exception if it cannot be found or cast.
                val predicateField = predicateFieldContainer.getPredicateFieldByProviderAndName(
                        ruleCondition.providerName!!, ruleCondition.fieldName!!
                ) ?: throw IllegalArgumentException("The given VehicleStateDataType did not match the field name it was registered under.")
                // Find the VehicleStateDataType this ConditionPredicate applies to.
                // If the given VehicleState doesn't provide the required Datum, return false
                val vehicleStateDataType = vehicleState.vehicleStateDataTypes?.let { vehicleStateDataTypes ->
                    vehicleStateDataTypes.find {
                        it.predicateFieldProviderName == ruleCondition.providerName!!
                    }
                } ?: return false

                return evaluationPredicateService.checkPredicate(
                        ruleCondition,
                        vehicleStateDataType,
                        predicateField
                )
            }
            else -> throw IllegalArgumentException("The given RuleCondition was of no known subtype.")
        }
    }
}
