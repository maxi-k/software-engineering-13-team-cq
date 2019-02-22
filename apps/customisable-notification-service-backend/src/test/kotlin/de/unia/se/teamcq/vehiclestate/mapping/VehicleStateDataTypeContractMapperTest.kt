package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeContractEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeContractModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VehicleStateDataTypeContractMapperTest : StringSpec() {

    @Autowired
    lateinit var vehicleStateDataTypeContractMapper: IVehicleStateDataTypeContractMapperImpl

    init {

        "Convert model to entity" {

            val vehicleStateDataTypeContract = getTestVehicleStateDataTypeContractModel()

            val vehicleStateDataTypeContractEntity = vehicleStateDataTypeContractMapper
                    .modelToEntity(vehicleStateDataTypeContract)

            vehicleStateDataTypeContractEntity shouldBe getTestVehicleStateDataTypeContractEntity()
        }

        "Convert entity to model" {

            val vehicleStateDataTypeContractEntity = getTestVehicleStateDataTypeContractEntity()

            val vehicleStateDataTypeContract = vehicleStateDataTypeContractMapper
                    .entityToModel(vehicleStateDataTypeContractEntity)

            vehicleStateDataTypeContract shouldBe getTestVehicleStateDataTypeContractModel()
        }
    }
}
