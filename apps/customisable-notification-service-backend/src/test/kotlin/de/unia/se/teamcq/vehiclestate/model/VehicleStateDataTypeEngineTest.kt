package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEngineModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeEngineTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeEngineModel,
                    { it.dataTypeId = 3 },
                    { it.capacity = 20 },
                    { it.fuelType = "test" },
                    { it.power = 20 }
            )
        }
    }
}
