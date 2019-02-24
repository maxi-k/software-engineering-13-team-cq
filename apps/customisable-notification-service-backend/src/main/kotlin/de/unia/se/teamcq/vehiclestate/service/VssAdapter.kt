package de.unia.se.teamcq.vehiclestate.service

import de.bmw.vss.ApiClient
import de.bmw.vss.api.VehicleStatesApi
import de.bmw.vss.model.Vehicle
import de.unia.se.teamcq.security.service.IAuthenticationTokenService
import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateAdapterMapper
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClientException
import org.threeten.bp.OffsetDateTime
import java.util.TimeZone
import java.util.UUID

@Component
@Transactional
class VssAdapter : IVssAdapter {

    @Autowired
    private lateinit var authenticationTokenService: IAuthenticationTokenService

    @Autowired
    private lateinit var vehicleStateAdapterMapper: IVehicleStateAdapterMapper

    @Throws(RestClientException::class, NullPointerException::class)
    override fun getNewVehicleStates(fleetReferences: Set<FleetReference>): List<VehicleState> {

        val (header, token) = authenticationTokenService.getAuthenticationHeader()

        val fetchedVehicles = fetchVehicles(header, token, fleetReferences)

        return fetchedVehicles.map { vehicle ->
            vehicleStateAdapterMapper.dtoToModel(vehicle)
        }
    }

    private fun fetchVehicles(
        header: String,
        token: String,
        fleetReferences: Set<FleetReference>
    ): List<Vehicle> {

        val apiInstance = VehicleStatesApi()
        val apiClient = ApiClient()
        apiClient.addDefaultHeader(header, token)
        apiInstance.apiClient = apiClient

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

        return fleetReferences.flatMap { fleetReference ->
            val fetchedFleetsResult = apiInstance.getAllVehicles(UUID.fromString(fleetReference.carParkId),
                listOf(UUID.fromString(fleetReference.fleetId)),
                OffsetDateTime.now().minusYears(1))

            fetchedFleetsResult.items
        }
    }
}
