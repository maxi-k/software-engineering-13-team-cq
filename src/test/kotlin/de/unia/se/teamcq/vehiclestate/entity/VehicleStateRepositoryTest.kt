package de.unia.se.teamcq.vehiclestate.entity

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

        "GetAllVehicleStates should work" {

            val vehicleStateEntityA = getTestVehicleStateEnity().copy(eventId = 1, kilometers = 2)
            val vehicleStateEntityB = getTestVehicleStateEnity().copy(eventId = 2, kilometers = 5)

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(vehicleStateEntityA)
            vehicleStateEntityRepository.save(vehicleStateEntityB)

            val allVehicleStates = vehicleStateRepository.getAllVehicleStates()

            allVehicleStates.size shouldBeGreaterThanOrEqual 2

            val expectedVehicleStateModel = getTestVehicleStateModel()
                    .copy(eventId = savedVehicleStateEntity.eventId, kilometers = 2)

            allVehicleStates.contains(expectedVehicleStateModel) shouldBe true
        }

        "GetVehicleState should work" {

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEnity())

            val actualVehicleState = vehicleStateRepository.getVehicleState(savedVehicleStateEntity.eventId!!)

            actualVehicleState shouldBe getTestVehicleStateModel().copy(eventId = savedVehicleStateEntity.eventId)
        }

        "CreateVehicleState should work" {

            val savedVehicleState = vehicleStateRepository.createVehicleState(getTestVehicleStateModel())

            savedVehicleState shouldBe getTestVehicleStateModel().copy(savedVehicleState!!.eventId)
        }

        "UpdateVehicleState should work" {

            val oldVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEnity())

            val newVehicleState = getTestVehicleStateModel().copy(eventId = oldVehicleStateEntity.eventId, kilometers = 3)

            val actualVehicleState = vehicleStateRepository.updateVehicleState(newVehicleState)!!

            actualVehicleState shouldBe newVehicleState
        }

        "DeleteVehicleState should work" {

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEnity())

            vehicleStateRepository.deleteVehicleState(savedVehicleStateEntity.eventId!!)

            vehicleStateEntityRepository.existsById(savedVehicleStateEntity.eventId!!) shouldBe false
        }
    }
}
