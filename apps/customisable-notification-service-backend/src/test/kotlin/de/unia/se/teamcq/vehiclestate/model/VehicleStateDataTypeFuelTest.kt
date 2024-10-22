package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeFuelModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeFuelTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeFuelModel,
                    { it.dataTypeId = 3 },
                    { it.levelPercentage = 20.0 },
                    { it.levelLiters = 20 },
                    { it.tankCapacity = 20.0 }
            )
        }
    }
}
