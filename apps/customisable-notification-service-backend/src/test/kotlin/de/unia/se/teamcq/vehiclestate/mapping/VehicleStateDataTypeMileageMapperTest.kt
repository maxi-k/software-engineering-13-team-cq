package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeMileageEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeMileageModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VehicleStateDataTypeMileageMapperTest : StringSpec() {

    @Autowired
    lateinit var vehicleStateDataTypeMileageMapper: IVehicleStateDataTypeMileageMapperImpl

    init {

        "Convert model to entity" {

            val vehicleStateDataTypeMileage = getTestVehicleStateDataTypeMileageModel()

            val vehicleStateDataTypeMileageEntity = vehicleStateDataTypeMileageMapper
                    .modelToEntity(vehicleStateDataTypeMileage)

            vehicleStateDataTypeMileageEntity shouldBe getTestVehicleStateDataTypeMileageEntity()
        }

        "Convert entity to model" {

            val vehicleStateDataTypeMileageEntity = getTestVehicleStateDataTypeMileageEntity()

            val vehicleStateDataTypeMileage = vehicleStateDataTypeMileageMapper
                    .entityToModel(vehicleStateDataTypeMileageEntity)

            vehicleStateDataTypeMileage shouldBe getTestVehicleStateDataTypeMileageModel()
        }
    }
}
