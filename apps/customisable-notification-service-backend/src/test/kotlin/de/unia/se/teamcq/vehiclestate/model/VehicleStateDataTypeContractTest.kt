package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeContractModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeContractTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeContractModel,
                    { it.dataTypeId = 3 },
                    { it.calendarWeek = 20 },
                    { it.duePerWeek = 2 },
                    { it.vins = setOf("test1", "test2") }
            )
        }
    }
}
