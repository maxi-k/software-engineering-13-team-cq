package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class VehicleStateRepositoryTest : StringSpec() {

    @Autowired
    lateinit var vehicleStateRepository: VehicleStateRepository

    @Autowired
    lateinit var vehicleStateEntityRepository: IVehicleStateEntityRepository

    init {
        MockKAnnotations.init(this)

        val vehicleStateModel = TestUtils.getTestVehicleStateModel()
        val vehicleStateEntity = TestUtils.getTestVehicleStateEnity()

        "getAllVehicleStates should work" {

            val vehicleStateEntityB = vehicleStateEntity.copy(eventId = 2, kilometers = 2)

            vehicleStateEntityRepository.save(vehicleStateEntity)
            vehicleStateEntityRepository.save(vehicleStateEntityB)

            val allVehicleStates = vehicleStateRepository.getAllVehicleStates()

            allVehicleStates.size shouldBeGreaterThanOrEqual 2

            allVehicleStates.contains(vehicleStateModel) shouldBe true
        }

        "getVehicleState should work" {

            vehicleStateEntityRepository.save(vehicleStateEntity)

            val actualVehicleState = vehicleStateRepository.getVehicleState(1)

            actualVehicleState shouldBe vehicleStateModel
        }

        "createVehicleState should work" {

            val savedVehicleState = vehicleStateRepository.createVehicleState(vehicleStateModel)

            savedVehicleState shouldBe vehicleStateModel
        }

        "updateVehicleState should work" {

            vehicleStateEntityRepository.save(vehicleStateEntity)

            val newVehicleState = vehicleStateModel.copy(kilometers = 3)

            val actualVehicleState = vehicleStateRepository.updateVehicleState(newVehicleState)!!

            actualVehicleState shouldBe newVehicleState
        }

        "deleteVehicleState should work" {

            vehicleStateEntityRepository.save(vehicleStateEntity)

            vehicleStateRepository.deleteVehicleState(1)

            vehicleStateEntityRepository.existsById(1) shouldBe false
        }
    }
}
