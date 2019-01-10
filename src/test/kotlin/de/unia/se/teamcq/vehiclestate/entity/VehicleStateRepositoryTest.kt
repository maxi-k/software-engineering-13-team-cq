package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.getTestVehicleStateEnity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
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
    lateinit var vehicleStateEntityRepository: IVehicleStateEntityRepository

    @Autowired
    lateinit var vehicleStateRepository: VehicleStateRepository

    init {
        MockKAnnotations.init(this)

        "getAllVehicleStates should work" {

            val vehicleStateEntityA = getTestVehicleStateEnity().copy(eventId = 1, kilometers = 2)
            val vehicleStateEntityB = getTestVehicleStateEnity().copy(eventId = 2, kilometers = 5)

            vehicleStateEntityRepository.save(vehicleStateEntityA)
            vehicleStateEntityRepository.save(vehicleStateEntityB)

            val allVehicleStates = vehicleStateRepository.getAllVehicleStates()

            allVehicleStates.size shouldBeGreaterThanOrEqual 2

            allVehicleStates.contains(getTestVehicleStateModel().copy(kilometers = 2)) shouldBe true
        }

        "getVehicleState should work" {

            vehicleStateEntityRepository.save(getTestVehicleStateEnity())

            val actualVehicleState = vehicleStateRepository.getVehicleState(1)

            actualVehicleState shouldBe getTestVehicleStateModel()
        }

        "createVehicleState should work" {

            val savedVehicleState = vehicleStateRepository.createVehicleState(getTestVehicleStateModel())

            savedVehicleState shouldBe getTestVehicleStateModel()
        }

        "updateVehicleState should work" {

            vehicleStateEntityRepository.save(getTestVehicleStateEnity())

            val newVehicleState = getTestVehicleStateModel().copy(kilometers = 3)

            val actualVehicleState = vehicleStateRepository.updateVehicleState(newVehicleState)!!

            actualVehicleState shouldBe newVehicleState
        }

        "deleteVehicleState should work" {

            vehicleStateEntityRepository.save(getTestVehicleStateEnity())

            vehicleStateRepository.deleteVehicleState(1)

            vehicleStateEntityRepository.existsById(1) shouldBe false
        }
    }
}
