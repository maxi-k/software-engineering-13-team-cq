package de.unia.se.teamcq.vehiclestate.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException

/**
 * The service that handles importing new VehicleStateData.
 */
@Service
interface IVehicleStateService {

    /**
     * Imports new data
     *
     * Imports new data into our DB that isn't already present.
     */
    @Throws(RestClientException::class, NullPointerException::class)
    fun importNewVehicleData()
}
