package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeServiceModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.util.Date

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeServiceTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeServiceModel,
                    { it.dataTypeId = 3 },
                    { it.status = "test" },
                    { it.dueDate = "test" },
                    { it.remainingMileage = 20 }
            )
        }
    }
}
