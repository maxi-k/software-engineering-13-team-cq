package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeServiceEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeServiceModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VehicleStateDataTypeServiceMapperTest : StringSpec() {

    @Autowired
    lateinit var vehicleStateDataTypeServiceMapper: IVehicleStateDataTypeServiceMapperImpl

    init {

        "Convert model to entity" {

            val vehicleStateDataTypeService = getTestVehicleStateDataTypeServiceModel()

            val vehicleStateDataTypeServiceEntity = vehicleStateDataTypeServiceMapper
                    .modelToEntity(vehicleStateDataTypeService)

            vehicleStateDataTypeServiceEntity shouldBe getTestVehicleStateDataTypeServiceEntity()
        }

        "Convert entity to model" {

            val vehicleStateDataTypeServiceEntity = getTestVehicleStateDataTypeServiceEntity()

            val vehicleStateDataTypeService = vehicleStateDataTypeServiceMapper
                    .entityToModel(vehicleStateDataTypeServiceEntity)

            vehicleStateDataTypeService shouldBe getTestVehicleStateDataTypeServiceModel()
        }
    }
}
