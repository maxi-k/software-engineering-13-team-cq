package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateEnity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateMapperTest : StringSpec() {

    private var vehicleStateMapper: IVehicleStateMapper = Mappers.getMapper(IVehicleStateMapper::class.java)

    init {

        "Convert model to entity" {

            val vehicleState = getTestVehicleStateModel()

            val vehicleStateEntity = vehicleStateMapper.modelToEntity(vehicleState)

            vehicleStateEntity shouldNotBe null
            vehicleStateEntity.eventId shouldBe vehicleState.eventId
            vehicleStateEntity.vehicleId shouldBe vehicleState.vehicleId
            vehicleStateEntity.kilometers shouldBe vehicleState.kilometers
            vehicleStateEntity.batteryCharge shouldBe vehicleState.batteryCharge
        }

        "Convert entity to model" {

            val vehicleStateEntity = getTestVehicleStateEnity()

            val vehicleState = vehicleStateMapper.entityToModel(vehicleStateEntity)

            vehicleState shouldNotBe null
            vehicleStateEntity.eventId shouldBe vehicleState.eventId
            vehicleStateEntity.vehicleId shouldBe vehicleState.vehicleId
            vehicleStateEntity.kilometers shouldBe vehicleState.kilometers
            vehicleStateEntity.batteryCharge shouldBe vehicleState.batteryCharge
        }
    }
}
