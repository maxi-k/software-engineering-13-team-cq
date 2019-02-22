package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeFuelEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeFuelModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VehicleStateDataTypeFuelMapperTest : StringSpec() {

    @Autowired
    lateinit var vehicleStateDataTypeFuelMapper: IVehicleStateDataTypeFuelMapperImpl

    init {

        "Convert model to entity" {

            val vehicleStateDataTypeFuel = getTestVehicleStateDataTypeFuelModel()

            val vehicleStateDataTypeFuelEntity = vehicleStateDataTypeFuelMapper
                    .modelToEntity(vehicleStateDataTypeFuel)

            vehicleStateDataTypeFuelEntity shouldBe getTestVehicleStateDataTypeFuelEntity()
        }

        "Convert entity to model" {

            val vehicleStateDataTypeFuelEntity = getTestVehicleStateDataTypeFuelEntity()

            val vehicleStateDataTypeFuel = vehicleStateDataTypeFuelMapper
                    .entityToModel(vehicleStateDataTypeFuelEntity)

            vehicleStateDataTypeFuel shouldBe getTestVehicleStateDataTypeFuelModel()
        }
    }
}
