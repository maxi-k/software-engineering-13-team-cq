package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeBatteryModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VehicleStateDataTypeBatteryMapperTest : StringSpec() {

    @Autowired
    lateinit var vehicleStateDataTypeBatteryMapper: IVehicleStateDataTypeBatteryMapperImpl

    init {

        "Convert model to entity" {

            val vehicleStateDataTypeBattery = getTestVehicleStateDataTypeBatteryModel()

            val vehicleStateDataTypeBatteryEntity = vehicleStateDataTypeBatteryMapper
                    .modelToEntity(vehicleStateDataTypeBattery)

            vehicleStateDataTypeBatteryEntity shouldBe getTestVehicleStateDataTypeBatteryEntity()
        }

        "Convert entity to model" {

            val vehicleStateDataTypeBatteryEntity = getTestVehicleStateDataTypeBatteryEntity()

            val vehicleStateDataTypeBattery = vehicleStateDataTypeBatteryMapper
                    .entityToModel(vehicleStateDataTypeBatteryEntity)

            vehicleStateDataTypeBattery shouldBe getTestVehicleStateDataTypeBatteryModel()
        }
    }
}
