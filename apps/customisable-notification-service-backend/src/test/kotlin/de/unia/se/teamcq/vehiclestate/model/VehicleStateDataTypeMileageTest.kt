package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeMileageModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeMileageTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeMileageModel,
                    { it.dataTypeId = 3 },
                    { it.current = 20 },
                    { it.reached = 20 },
                    { it.remaining = 20 }
            )
        }
    }
}
