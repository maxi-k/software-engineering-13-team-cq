package de.unia.se.teamcq.ruleevaluation.service

import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Service

@Service
interface IEvaluationService {

    fun checkCondition(ruleCondition: RuleCondition, vehicleState: VehicleState): Boolean
}
