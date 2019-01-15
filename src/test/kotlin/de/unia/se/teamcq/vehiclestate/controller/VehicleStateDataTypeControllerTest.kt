package de.unia.se.teamcq.vehiclestate.controller

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeDto
import de.unia.se.teamcq.vehiclestate.mapping.IVehicleStateDataTypeMapper
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import de.unia.se.teamcq.vehiclestate.service.IVehicleStateService
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateDataTypeControllerTest : StringSpec() {

    @MockK
    private lateinit var vehicleStateDataTypeMapper: IVehicleStateDataTypeMapper

    @MockK
    private lateinit var vehicleStateService: IVehicleStateService

    @InjectMockKs
    private lateinit var vehicleStateDataTypeController: VehicleStateDataTypeController

    init {
        MockKAnnotations.init(this)

        every { vehicleStateService.getVehicleStateDataTypes() } returns setOf(VehicleStateDataTypeBattery())

        every { vehicleStateDataTypeMapper.modelToDto(any()) } returns getTestVehicleStateDataTypeDto()

        val mockMvc = MockMvcBuilders
                .standaloneSetup(vehicleStateDataTypeController)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()

        "GetVehicleStateDataTypes should work" {

            val possibleRequestPaths = listOf(
                    "/vehicle-state-data-types",
                    "/vehicle-state-data-types/"
            )

            possibleRequestPaths.map { requestPath ->

                mockMvc.perform(MockMvcRequestBuilders
                        .get(requestPath))
                        .andExpect(status().isOk)
                        .andExpect(jsonPath("$", hasSize<Int>(1)))
                        .andExpect(jsonPath("$[0].name", `is`("Battery")))
            }
        }
    }
}
