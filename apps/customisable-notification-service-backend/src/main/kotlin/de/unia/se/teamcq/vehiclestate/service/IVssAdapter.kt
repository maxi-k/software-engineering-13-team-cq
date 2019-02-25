package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException

/**
 * The service that handles fetching new VehicleState Data.
 */
@Service
interface IVssAdapter {

    /**
     * Fetch new [VehicleState]s
     *
     * @param fleetReferences A set with all [FleetReference] to fetch [VehicleState]s for
     * @return All new [VehicleState]s
     */
    @Throws(RestClientException::class, NullPointerException::class)
    fun getNewVehicleStates(fleetReferences: List<FleetReference>): List<VehicleState>
}
