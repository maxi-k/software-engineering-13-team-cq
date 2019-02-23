package de.unia.se.teamcq.vehiclestate.service

import de.bmw.authentication.api.LoginApi
import de.bmw.authentication.model.Login
import de.bmw.vss.ApiClient
import de.bmw.vss.api.VehicleStatesApi
import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.threeten.bp.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID
import java.util.TimeZone



@Component
@Transactional
class VehicleStateService : IVehicleStateService {

    @Value("\${de.unia.se.teamcq.bmw-authentication.username:name}")
    private lateinit var authenticationUsername: String

    @Value("\${de.unia.se.teamcq.bmw-authentication.password:pw}")
    private lateinit var authenticationPassword: String

    @Autowired
    lateinit var vehicleStateRepository: IVehicleStateRepository

    override fun importNewVehicleData() {
        val loginApi = LoginApi()
        var loginResult: Login? = null
        try {
            loginResult = loginApi.login(authenticationUsername, authenticationPassword)
            logger.info("Login successful", loginResult.toString())
        } catch (exception: Exception) {
            logger.error("Exception when calling LoginApi#login", exception)
        }

        val apiInstance = VehicleStatesApi()
        val apiClient = ApiClient()
        apiClient.addDefaultHeader("Authorization", "Bearer ${loginResult!!.accessToken}")
        apiInstance.apiClient = apiClient

        val uuidForLogging = UUID.randomUUID().toString()
        val dateTime = ZonedDateTime.now().minusYears(1)
        val dateTimeStringIso8601 = dateTimeFormatter.format(dateTime)

        try {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
            val sendMailResult = apiInstance.getAllVehicles(UUID.fromString("cccccccc-0000-cccc-0000-000000000099"),
                    listOf(UUID.fromString("cccccccc-0000-ffff-0000-000000000099")),
                    OffsetDateTime.now().minusYears(1)
            )
            logger.info("Sending E-Mail successful!", sendMailResult)
        } catch (exception: Exception) {
            logger.error("Exception when calling EmailV1Api#getEmailMessageTemplate", exception)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleStateService::class.java)

        private val dateTimeFormatter = DateTimeFormatter.ISO_INSTANT
    }
}
