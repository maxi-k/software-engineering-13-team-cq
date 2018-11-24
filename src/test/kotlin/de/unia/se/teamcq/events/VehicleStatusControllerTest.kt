package de.unia.se.teamcq.events

import com.google.gson.Gson
import de.unia.se.teamcq.events.controller.VehicleStatusController
import de.unia.se.teamcq.events.model.VehicleStatus
import de.unia.se.teamcq.events.service.VehicleStatusService
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.Optional
import java.util.UUID

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStatusControllerTest : StringSpec() {

    @MockK
    private lateinit var vehicleStatusService: VehicleStatusService

    @InjectMockKs
    private lateinit var vehicleStatusController: VehicleStatusController

    private var mockMvc: MockMvc
    private val gson = Gson()

    init {
        MockKAnnotations.init(this)

        mockMvc = MockMvcBuilders
                .standaloneSetup(vehicleStatusController)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()

        // Define a vehicle status we are working with
        val mockedVehicleStatus = VehicleStatus(
                eventId = 42,
                vehicleId = UUID.randomUUID().toString(),
                kilometers = 200,
                batteryCharge = 0.5f)

        // Define what the mocked service should return
        // - 'create' should return just the passed object
        every { vehicleStatusService.createNewVehicleStatus(any()) } answers { arg(0) }
        // - 'get' should return the only status we know, `mockedVehicleStatus`
        every { vehicleStatusService.getVehicleStatusById(mockedVehicleStatus.eventId) } returns Optional.of(mockedVehicleStatus)
        every { vehicleStatusService.getVehicleStatusById(not(mockedVehicleStatus.eventId)) } returns Optional.empty()

        "inserts vehicle status successfully" {
            // Perform a POST request to /events/vehicle-status
            mockMvc.perform(MockMvcRequestBuilders
                    .post("/events/vehicle-status")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(gson.toJson(mockedVehicleStatus)))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedVehicleStatus = gson.fromJson(
                                result.response.contentAsString,
                                VehicleStatus::class.java)

                        returnedVehicleStatus shouldBe mockedVehicleStatus
                    }

            // Verify that the mocked service was called exactly once
            verify(exactly = 1) {
                vehicleStatusService.createNewVehicleStatus(mockedVehicleStatus)
            }
        }

        "returns the vehicle status stored" {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/events/vehicle-status/${mockedVehicleStatus.eventId}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk)
                    .andExpect { result ->
                        val returnedVehicleStatus = gson.fromJson(
                                result.response.contentAsString,
                                VehicleStatus::class.java)

                        returnedVehicleStatus shouldBe mockedVehicleStatus
                    }

            verify(exactly = 1) {
                vehicleStatusService.getVehicleStatusById(any())
            }
        }

        "returns a 404 error if there is no such status" {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/events/vehicle-status/1029")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNotFound)

            verify(exactly = 1) {
                vehicleStatusService.getVehicleStatusById(not(mockedVehicleStatus.eventId))
            }
        }
    }
}
