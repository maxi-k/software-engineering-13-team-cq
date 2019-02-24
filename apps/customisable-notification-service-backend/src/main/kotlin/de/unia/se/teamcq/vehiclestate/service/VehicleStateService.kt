package de.unia.se.teamcq.vehiclestate.service

import de.bmw.vss.ApiClient
import de.bmw.vss.api.VehicleStatesApi
import de.bmw.vss.model.Vehicle
import de.unia.se.teamcq.security.service.IAuthenticationTokenService
import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClientException
import org.threeten.bp.OffsetDateTime
import java.util.TimeZone
import java.util.UUID

@Component
@Transactional
class VehicleStateService : IVehicleStateService {

    @Autowired
    private lateinit var authenticationTokenService: IAuthenticationTokenService

    @Autowired
    private lateinit var vehicleStateRepository: IVehicleStateRepository

    @Throws(RestClientException::class, NullPointerException::class)
    override fun importNewVehicleData() {

        val (header, token) = authenticationTokenService.getAuthenticationHeader()

        val fetchedVehicles = fetchVehicles(header, token)

        logger.info("Importing VehicleState successful!", fetchedVehicles)
    }

    private fun fetchVehicles(header: String, token: String): List<Vehicle> {
        val apiInstance = VehicleStatesApi()
        val apiClient = ApiClient()
        apiClient.addDefaultHeader(header, token)
        apiInstance.apiClient = apiClient

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

        val sendMailResult = apiInstance.getAllVehicles(UUID.fromString("cccccccc-0000-cccc-0000-000000000099"),
                listOf(UUID.fromString("cccccccc-0000-ffff-0000-000000000099")),
                OffsetDateTime.now().minusYears(1)
        )
        return sendMailResult.items
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateService::class.java)
    }
}
