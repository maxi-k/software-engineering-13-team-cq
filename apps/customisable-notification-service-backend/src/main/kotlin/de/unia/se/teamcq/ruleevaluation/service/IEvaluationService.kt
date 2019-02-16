package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Service

/**
 * A service to evaluate [RuleCondition]s.
 */
@Service
interface IEvaluationService {

    /**
     * Evaluate a [RuleCondition] on a [VehicleState]
     *
     * @param ruleCondition The [RuleCondition] to evaluate
     * @param vehicleState The [VehicleState] to evaluate the condition on
     * @returns The evaluated [RuleCondition]
     */
    fun checkCondition(ruleCondition: RuleCondition, vehicleState: VehicleState): Boolean
}
