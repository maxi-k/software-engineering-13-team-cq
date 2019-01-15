package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataType
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeDto
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateDataTypeMapperTest : StringSpec() {

    private var vehicleStateDataTypeMapper: IVehicleStateDataTypeMapper = Mappers.getMapper(IVehicleStateDataTypeMapper::class.java)

    init {

        "Convert model to dto" {

            val vehicleStateDataType = getTestVehicleStateDataType()

            val vehicleStateDataTypeDto = vehicleStateDataTypeMapper.modelToDto(vehicleStateDataType)
            val expectedVehicleStateDataTypeDto = getTestVehicleStateDataTypeDto()

            vehicleStateDataTypeDto shouldNotBe null
            vehicleStateDataTypeDto.name shouldBe expectedVehicleStateDataTypeDto.name
        }
    }
}
