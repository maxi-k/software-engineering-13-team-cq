package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.ruleevaluation.service.IEvaluationService
import de.unia.se.teamcq.rulemanagement.service.INotificationRuleService
import de.unia.se.teamcq.vehiclestate.service.VehicleStateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RuleStateProcessingService : IRuleStateProcessingService {

    @Autowired
    private lateinit var notificationRuleService: INotificationRuleService

    @Autowired
    private lateinit var evaluationService: IEvaluationService

    @Autowired
    private lateinit var notificationService: INotificationService

    @Autowired
    private lateinit var vehicleStateService: VehicleStateService

    override fun processNewVehicleStatesForRule(ruleId: Long) {

        val notificationRule = notificationRuleService.getNotificationRule(ruleId)
        val vehicleStatesToProcess = vehicleStateService.getUnprocessedVehicleStateForRule(notificationRule!!)

        val vehicleStateMatches = vehicleStatesToProcess.filter { vehicleState ->
            evaluationService.checkCondition(notificationRule.condition!!, vehicleState)
        }.toSet()

        val notificationData = NotificationData(notificationRule, vehicleStateMatches)
        notificationService.storeNotificationData(notificationData)

        vehicleStateService.markVehicleStateAsProcessedByRule(notificationRule, vehicleStatesToProcess)

        notificationService.sendNotificationForRuleIfNecessary(notificationRule)
    }
}
