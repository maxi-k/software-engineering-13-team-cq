package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEngineEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEngineModel
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VehicleStateDataTypeEngineMapperTest : StringSpec() {

    @Autowired
    lateinit var vehicleStateDataTypeEngineMapper: IVehicleStateDataTypeEngineMapperImpl

    init {

        "Convert model to entity" {

            val vehicleStateDataTypeEngine = getTestVehicleStateDataTypeEngineModel()

            val vehicleStateDataTypeEngineEntity = vehicleStateDataTypeEngineMapper
                    .modelToEntity(vehicleStateDataTypeEngine)

            vehicleStateDataTypeEngineEntity shouldBe getTestVehicleStateDataTypeEngineEntity()
        }

        "Convert entity to model" {

            val vehicleStateDataTypeEngineEntity = getTestVehicleStateDataTypeEngineEntity()

            val vehicleStateDataTypeEngine = vehicleStateDataTypeEngineMapper
                    .entityToModel(vehicleStateDataTypeEngineEntity)

            vehicleStateDataTypeEngine shouldBe getTestVehicleStateDataTypeEngineModel()
        }
    }
}
