package de.unia.se.teamcq.vehiclestate.service

import de.bmw.vss.ApiClient
import de.bmw.vss.api.VehicleStatesApi
import de.bmw.vss.model.Vehicle
import de.unia.se.teamcq.security.service.IAuthenticationTokenAdapter
import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateAdapterMapper
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.quartz.CronExpression
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClientException
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import java.util.Date
import java.util.TimeZone
import java.util.UUID

@Component
@Transactional
class VssAdapter : IVssAdapter {

    @Value("\${de.unia.se.teamcq.scheduling.data-import-cron:0 0/1 * * * ?}")
    private lateinit var dataImportCronString: String

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
        val vehicleStatesSince = getLastJobExecution()

        return fleetReferences.flatMap { fleetReference ->

            val fetchedFleetsResult = apiInstance.getAllVehicles(UUID.fromString(fleetReference.carParkId),
                listOf(UUID.fromString(fleetReference.fleetId)),
                vehicleStatesSince
            )

            fetchedFleetsResult.items
        }
    }

    private fun getLastJobExecution(): OffsetDateTime {
        val dataProcessingLastExecutionInstant = CronExpression(dataImportCronString)
                .getNextValidTimeAfter(Date())
                .toInstant()

        val threeTenInstant = Instant.ofEpochMilli(
                dataProcessingLastExecutionInstant.toEpochMilli()
        )

        return OffsetDateTime.ofInstant(threeTenInstant, ZoneOffset.UTC)
                .minusDays(1)
    }
}
