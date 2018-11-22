package de.unia.se.teamcq.mock.vehicle.status

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.UUID
import kotlin.random.Random
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled

@Service
class VehicleStatusMockService {

    @Autowired
    lateinit var environment: Environment

    val maxVehiclesMocked = 100
    val knownVehicles by lazy {
        generateSequence { UUID.randomUUID() }
                .take(maxVehiclesMocked)
                .toList()
    }

    val restTemplate by lazy {
        val template = RestTemplate()
        template
    }

    val requestHeaders by lazy {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = mutableListOf(MediaType.APPLICATION_JSON)
        headers
    }

    data class VehicleStatus(
        val vehicleId: String,
        val kilometers: Int,
        val batteryCharge: Float
    )
    val gsonConverter = Gson()

    private fun <T : Any> wrapRequestObject(obj: T): HttpEntity<String> {
        return HttpEntity(gsonConverter.toJson(obj), requestHeaders)
    }

    @Scheduled(fixedDelayString = "\${de.unia.se.teamcq.mock.status.interval}")
    fun sendMockedStatusAtInterval() {

        val serverPort = environment.getProperty("server.port")
                ?: environment.getProperty("local.server.port")
                ?: 8080
        val mockedEndpoint = "http://0.0.0.0:$serverPort/events/vehicle-status"

        val vehicleUUID = knownVehicles[Random.nextInt(knownVehicles.size)]
        val vehicleStatus = VehicleStatus(vehicleUUID.toString(), Random.nextInt(50000), Random.nextFloat())

        try {
            restTemplate.postForEntity(mockedEndpoint, wrapRequestObject(vehicleStatus), String::class.java)
        } catch (e: Exception) {
            // TODO
        }
    }
}