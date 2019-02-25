package de.unia.se.teamcq.ruleevaluation

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.security.JwtConfig
import de.unia.se.teamcq.security.JwtTokenAuthenticationFilter
import io.kotlintest.specs.StringSpec
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.hasItems
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PredicateFieldControllerIntegrationTest : StringSpec() {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var jwtTokenAuthenticationFilter: JwtTokenAuthenticationFilter

    @Autowired
    lateinit var jwtConfig: JwtConfig

    val possibleRequestPaths = listOf(
            "/predicate-fields",
            "/predicate-fields/"
    )

    init {
        "Fail without Authorization" {

            val mockMvc = TestUtils.buildMockMvc(webApplicationContext)

            possibleRequestPaths.map { requestPath ->

                mockMvc.perform(MockMvcRequestBuilders
                        .get(requestPath)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isUnauthorized)
            }
        }

        "GetPredicateFieldProviders should work" {

            val mockMvc = TestUtils.buildMockMvc(webApplicationContext)

            val accessToken = jwtTokenAuthenticationFilter.createToken("Max Mustermann")

            val expectingAtLeastPredicateFieldProviders = listOf("Battery", "Contract", "Engine",
                    "Fuel", "Mileage", "Service")
            val expectingAtLeastPossibleDataTypes = arrayOf("PERCENTAGE_DECIMAL", "VOLTAGE", "TEXT", "HOUR", "LITER",
                    "KILOMETER", "DATE", "PERCENTAGE_INT", "DAY", "HORSEPOWER", "CAPACITY", "PERCENTAGE_DECIMAL")
            val expectingAtLeastPossibleEvaluationStrategies = arrayOf("EQUAL_TO", "NOT_EQUAL_TO", "GREATER_THAN",
                    "LESS_THAN", "GREATER_THAN_OR_EQUAL_TO", "LESS_THAN_OR_EQUAL_TO")

            possibleRequestPaths.map { requestPath ->

                mockMvc.perform(MockMvcRequestBuilders
                        .get(requestPath)
                        .headers(TestUtils.prepareAccessTokenHeader(jwtConfig, accessToken)))
                        .andExpect(status().isOk)
                        .andExpect(jsonPath("$", hasSize<Int>(greaterThanOrEqualTo(6))))
                        .andExpect(jsonPath("$[*].providerName", equalTo(expectingAtLeastPredicateFieldProviders)))
                        .andExpect(jsonPath("$[*].predicateFields[*].dataType",
                                hasItems(*expectingAtLeastPossibleDataTypes)))
                        .andExpect(jsonPath("$[*].predicateFields[*].possibleEvaluationStrategies[*]",
                                hasItems(*expectingAtLeastPossibleEvaluationStrategies)))
            }
        }
    }
}
