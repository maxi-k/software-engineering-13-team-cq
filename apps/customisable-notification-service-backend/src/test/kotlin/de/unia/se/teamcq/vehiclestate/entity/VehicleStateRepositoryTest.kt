package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils.getTestVehicleStateEntity
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

            val vehicleStateEntityA = getTestVehicleStateEntity()
            val vehicleStateEntityB = getTestVehicleStateEntity()

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(vehicleStateEntityA)
            vehicleStateEntityRepository.save(vehicleStateEntityB)

            val allVehicleStates = vehicleStateRepository.getAllVehicleStates()

            allVehicleStates.size shouldBeGreaterThanOrEqual 2

            val savedVehicleStateEntityInResult = allVehicleStates.any { vehicleState ->
                vehicleState.stateId == savedVehicleStateEntity.stateId &&
                        vehicleState.vehicleReference == getTestVehicleStateModel().vehicleReference
            }

            savedVehicleStateEntityInResult shouldBe true
        }

        "GetVehicleState should work" {

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity())

            val actualVehicleState = vehicleStateRepository.getVehicleState(savedVehicleStateEntity.stateId!!)

            actualVehicleState shouldBe getTestVehicleStateModel().copy(stateId = savedVehicleStateEntity.stateId)
        }

        "CreateVehicleState should work" {

            val savedVehicleState = vehicleStateRepository.createVehicleState(getTestVehicleStateModel())

            savedVehicleState shouldBe getTestVehicleStateModel().copy(savedVehicleState!!.stateId)
        }

        "UpdateVehicleState should work" {

            val oldVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity())

            val newVehicleState = getTestVehicleStateModel().copy(stateId = oldVehicleStateEntity.stateId)

            val actualVehicleState = vehicleStateRepository.updateVehicleState(newVehicleState)!!

            actualVehicleState shouldBe newVehicleState
        }

        "DeleteVehicleState should work" {

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity())

            vehicleStateRepository.deleteVehicleState(savedVehicleStateEntity.stateId!!)

            vehicleStateEntityRepository.existsById(savedVehicleStateEntity.stateId!!) shouldBe false
        }
    }
}
