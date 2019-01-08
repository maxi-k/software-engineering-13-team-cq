package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import io.kotlintest.specs.StringSpec
import org.assertj.core.api.Assertions.assertThat
import org.mapstruct.factory.Mappers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import kotlin.test.assertEquals

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateMapperTest : StringSpec() {

    private var vehicleStateMapper: VehicleStateMapper = Mappers.getMapper(VehicleStateMapper::class.java)

    init {

        "convert model to entity" {
            // Perform a POST request to /events/vehicle-status
            // given
            val vehicleState = VehicleState(
                    2,
                    "name",
                    10,
                    0.2
            )

            // when
            val vehicleStateEntity = vehicleStateMapper.modelToEntity(vehicleState)

            // then
            assertThat(vehicleStateEntity).isNotNull
            assertEquals(vehicleStateEntity.eventId, vehicleState.eventId)
            assertEquals(vehicleStateEntity.vehicleId, vehicleState.vehicleId)
            assertEquals(vehicleStateEntity.kilometers, vehicleState.kilometers)
            assertEquals(vehicleStateEntity.batteryCharge, vehicleState.batteryCharge)
        }

        "convert entity to model" {
            // given
            val vehicleStateEntity = VehicleStateEntity(
                    2,
                    "name",
                    10,
                    0.2
            )

            // when
            val vehicleState = vehicleStateMapper.entityToModel(vehicleStateEntity)

            // then
            assertThat(vehicleStateEntity).isNotNull
            assertEquals(vehicleStateEntity.eventId, vehicleState.eventId)
            assertEquals(vehicleStateEntity.vehicleId, vehicleState.vehicleId)
            assertEquals(vehicleStateEntity.kilometers, vehicleState.kilometers)
            assertEquals(vehicleStateEntity.batteryCharge, vehicleState.batteryCharge)
        }
    }
}
