package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeContractEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.sql.Date

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeContractEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeContractEntity,
                    { it.dataTypeId = 3 },
                    { it.endDate = Date(0) },
                    { it.startDate = Date(0) },
                    { it.reachedRuntimePercentage = 2 },
                    { it.remainingDays = 2 },
                    { it.startMileage = 2 }
            )
        }
    }
}
