package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Repository

@Repository
interface IVehicleStateRepository {

    fun getAllVehicleStates(): List<VehicleState>

    fun getVehicleState(vehicleStateId: Long): VehicleState?

    fun createVehicleStates(vehicleStates: List<VehicleState>): List<VehicleState>

    fun deleteVehicleState(vehicleStateId: Long)

    fun getAllFleetReferences(): List<FleetReference>

    fun getUnprocessedVehicleStateForRule(notificationRule: NotificationRule): List<VehicleState>

    fun markVehicleStateAsProcessedByRule(
        notificationRule: NotificationRule,
        vehicleStates: List<VehicleState>
    )
}
