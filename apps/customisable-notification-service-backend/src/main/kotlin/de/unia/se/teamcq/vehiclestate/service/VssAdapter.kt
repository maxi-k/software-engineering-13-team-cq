package de.unia.se.teamcq.vehiclestate.service

import de.bmw.vss.ApiClient
import de.bmw.vss.api.VehicleStatesApi
import de.bmw.vss.model.Vehicle
import de.unia.se.teamcq.security.service.IAuthenticationTokenAdapter
import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateAdapterMapper
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClientException
import org.threeten.bp.OffsetDateTime
import java.lang.IllegalArgumentException
import java.util.TimeZone
import java.util.UUID

@Component
@Transactional
class VssAdapter : IVssAdapter {

    @Autowired
    private lateinit var authenticationTokenAdapter: IAuthenticationTokenAdapter

    @Autowired
    private lateinit var vehicleStateAdapterMapper: IVehicleStateAdapterMapper

    @Throws(RestClientException::class, NullPointerException::class)
    override fun getNewVehicleStates(fleetReferences: List<FleetReference>): List<VehicleState> {

        val (header, token) = authenticationTokenAdapter.getAuthenticationHeader()

        val fetchedVehicles = fetchVehicles(header, token, fleetReferences)

        return fetchedVehicles.map { vehicle ->
            vehicleStateAdapterMapper.dtoToModel(vehicle)
        }
    }

    private fun fetchVehicles(
        header: String,
        token: String,
        fleetReferences: List<FleetReference>
    ): List<Vehicle> {

        val apiInstance = VehicleStatesApi()
        val apiClient = ApiClient()
        apiClient.addDefaultHeader(header, token)
        apiInstance.apiClient = apiClient

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

        return fleetReferences.flatMap { fleetReference ->

            try {

                val carParkIdUUID = UUID.fromString(fleetReference.carParkId)
                val fleetIdUUID = UUID.fromString(fleetReference.fleetId)

                val fetchedFleetsResult = apiInstance.getAllVehicles(
                        carParkIdUUID,
                        listOf(fleetIdUUID),
                        // FIXME: If used in prod: Replace this value which is currently set like this
                        // FIXME: to make testing easier with minus 1 day or time since last job execution
                        OffsetDateTime.now().minusYears(1)
                )

                fetchedFleetsResult.items
            } catch (illegalArgumentException: IllegalArgumentException) {
                logger.error("An invalid fleetReference was found: $fleetReference", illegalArgumentException)
                listOf<Vehicle>()
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VssAdapter::class.java)
    }
}
