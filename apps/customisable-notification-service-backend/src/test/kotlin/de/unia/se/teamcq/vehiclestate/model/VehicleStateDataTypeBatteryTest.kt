package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeBatteryModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeBatteryTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeBatteryModel,
                    { it.dataTypeId = 3 },
                    { it.charge = 20.0 },
                    { it.chargingStatus = "test" },
                    { it.remainingRange = 2 },
                    { it.voltage = 20.0 }
            )
        }
    }
}
