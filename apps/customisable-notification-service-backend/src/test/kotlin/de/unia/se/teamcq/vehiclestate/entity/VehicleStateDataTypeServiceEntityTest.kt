package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeServiceEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.util.Date

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeServiceEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeServiceEntity,
                    { it.dataTypeId = 3 },
                    { it.brakeFluid = "test" },
                    { it.dueDate = Date(10) },
                    { it.status = "test" }
            )
        }
    }
}
