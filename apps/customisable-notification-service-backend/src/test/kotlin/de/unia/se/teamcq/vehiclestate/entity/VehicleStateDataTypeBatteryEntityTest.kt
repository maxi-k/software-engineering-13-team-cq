package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeBatteryEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeBatteryEntity,
                    { it.dataTypeId = 3 },
                    { it.charge = 20.0 },
                    { it.chargingStatus = "test" },
                    { it.remainingRange = 2 },
                    { it.voltage = 20.0 }
            )
        }
    }
}
