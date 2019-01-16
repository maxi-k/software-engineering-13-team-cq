package de.unia.se.teamcq.ruleevaluation.controller

import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviderDto
import de.unia.se.teamcq.ruleevaluation.service.IPredicateFieldContainer
import de.unia.se.teamcq.ruleevaluation.mapping.IPredicateFieldMapper
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
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
class PredicateFieldControllerTest : StringSpec() {

    @MockK
    private lateinit var predicateFieldMapper: IPredicateFieldMapper

    @MockK
    private lateinit var predicateFieldContainer: IPredicateFieldContainer

    @InjectMockKs
    private lateinit var predicateFieldController: PredicateFieldController

    init {
        MockKAnnotations.init(this)

        every { predicateFieldContainer.getPredicateFieldProviders() } returns setOf(VehicleStateDataTypeBattery())

        every { predicateFieldMapper.modelToDto(any()) } returns getTestPredicateFieldProviderDto()

        val mockMvc = MockMvcBuilders
                .standaloneSetup(predicateFieldController)
                .setMessageConverters(MappingJackson2HttpMessageConverter())
                .build()

        "GetPredicateFieldProviders should work" {

            val possibleRequestPaths = listOf(
                    "/predicate-fields",
                    "/predicate-fields/"
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
