package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.ClassCastException

typealias RuleConditionReducer = (RuleCondition, RuleCondition) -> Boolean
typealias PredicateReducer = (predicates: Iterable<RuleCondition>, predicate: (RuleCondition) -> Boolean) -> Boolean

@Component
class EvaluationService : IEvaluationService {

    @Autowired
    lateinit var predicateFieldContainer: IPredicateFieldContainer

    @Autowired
    lateinit var evaluationPredicateService: IEvaluationPredicateService

    override fun checkCondition(ruleCondition: RuleCondition, vehicleState: VehicleState): Boolean {
        when (ruleCondition) {
            is RuleConditionComposite -> {
                ruleCondition.logicalConnective?.let {
                    val predicateReducer = getPredicateReducerFromLogicalConnective(it)
                    return predicateReducer(ruleCondition.subConditions) { subCondition ->
                        checkCondition(subCondition, vehicleState)
                    }
                }
                // TODO: Throw or return false if there is no connective?
                return false
            }
            is RuleConditionPredicate -> {
                if (ruleCondition.providerName.isNullOrBlank() ||
                        ruleCondition.fieldName.isNullOrBlank()) {
                    // TODO: Throw or return false if there are no names set?
                    return false
                }
                predicateFieldContainer.getPredicateFieldByProviderAndName(
                        ruleCondition.providerName!!, ruleCondition.fieldName!!
                )?.let { predicateField ->
                    vehicleState.vehicleStateDataTypes?.let { vehicleStateDataTypes ->
                        vehicleStateDataTypes.find {
                            (it as IPredicateFieldProvider).predicateFieldProviderName === ruleCondition.providerName!!
                        }
                    }?.let { vehicleStateDataType ->
                        return try {
                            @Suppress("UNCHECKED_CAST")
                            evaluationPredicateService.checkPredicate(
                                    ruleCondition,
                                    vehicleStateDataType,
                                    predicateField as PredicateField<VehicleStateDataType, Any>
                            )
                        } catch (e: ClassCastException) {
                            // TODO: Throw or return false if the cast failed?
                            false
                        }
                    }
                }
                return false
            }
            // TODO: Throw or return false if the argument was of the wrong subtype?
            else -> return false
        }
    }

    // TODO: Evaluate whether this is implemented better with
    // polymorphism over LogicalConnectiveType instead of a `when`
    private fun getPredicateReducerFromLogicalConnective(logicalConnectiveType: LogicalConnectiveType): PredicateReducer =
            when (logicalConnectiveType) {
                LogicalConnectiveType.ALL -> { iterable, predicate ->
                    iterable.all(predicate)
                }
                LogicalConnectiveType.ANY -> { iterable, predicate ->
                    iterable.any(predicate)
                }
                LogicalConnectiveType.NONE -> { iterable, predicate ->
                    iterable.none(predicate)
                }
            }
}
