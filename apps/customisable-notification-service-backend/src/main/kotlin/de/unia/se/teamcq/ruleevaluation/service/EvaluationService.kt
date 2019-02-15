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
    override fun checkCondition(ruleCondition: RuleCondition, vehicleState: VehicleState): Boolean =
            when (ruleCondition) {
                is RuleConditionComposite -> checkRuleConditionComposite(ruleCondition, vehicleState)
                is RuleConditionPredicate -> checkRuleConditionPredicate(ruleCondition, vehicleState)
                else -> throw IllegalArgumentException(
                        "The given RuleCondition $ruleCondition was of no known subtype."
                )
            }

    private fun checkRuleConditionComposite(
        ruleCondition: RuleConditionComposite,
        vehicleState: VehicleState
    ): Boolean {
        val predicateReducer = ruleCondition.logicalConnective?.getPredicateReducer()
                ?: throw IllegalArgumentException(
                        "Given composite NotificationRule $ruleCondition did not have a logical connective."
                )
        return predicateReducer(ruleCondition.subConditions) { subCondition ->
            checkCondition(subCondition, vehicleState)
        }
    }

    private fun checkRuleConditionPredicate(
        ruleCondition: RuleConditionPredicate,
        vehicleState: VehicleState
    ): Boolean {
        // If the condition did not supply a provider and a target field,
        // throw an exception. Don't return false, as a ConditionComposite with Connective NONE
        // could apply, even though the condition didn't even apply to a field
        if (ruleCondition.providerName.isNullOrBlank() || ruleCondition.fieldName.isNullOrBlank()) {
            throw IllegalArgumentException(
                    "Given predicate NotificationRule $ruleCondition did not specify a target PredicateField"
            )
        }
        // Find the PredicateField this Condition should use,
        // throw an exception if it cannot be found or cast.
        val predicateField = predicateFieldContainer.getPredicateFieldByProviderAndName(
                ruleCondition.providerName!!, ruleCondition.fieldName!!
        ) ?: throw IllegalArgumentException(
                "No PredicateField under the name ${ruleCondition.providerName}.${ruleCondition.fieldName} " +
                        "could be found, which is what the $ruleCondition should be applied to."
        )
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
}
