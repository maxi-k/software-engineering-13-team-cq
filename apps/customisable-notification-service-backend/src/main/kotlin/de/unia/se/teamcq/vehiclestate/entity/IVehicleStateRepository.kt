package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Repository

@Repository
interface IVehicleStateRepository {

    fun getAllVehicleStates(): List<VehicleState>

    fun getVehicleState(vehicleStateId: Long): VehicleState?

    fun createVehicleState(vehicleState: VehicleState): VehicleState?

    fun updateVehicleState(vehicleState: VehicleState): VehicleState?

    fun deleteVehicleState(vehicleStateId: Long)

    fun getAllFleetReferences(): List<FleetReference>
}
