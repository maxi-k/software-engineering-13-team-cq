package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils.getTestVehicleStateEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import io.kotlintest.matchers.collections.shouldContain
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

            val savedVehicleStateModel = vehicleStateRepository.getVehicleState(savedVehicleStateEntity.stateId!!)

            allVehicleStates shouldContain savedVehicleStateModel
        }

        "GetVehicleState should work" {

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity())

            val actualVehicleState = vehicleStateRepository.getVehicleState(savedVehicleStateEntity.stateId!!)

            val expectedVehicleStateModel = getTestVehicleStateModel()
            expectedVehicleStateModel.setIdsOfRelatedRepositoryEntities(actualVehicleState!!)

            actualVehicleState shouldBe expectedVehicleStateModel
        }

        "CreateVehicleState should work" {

            val savedVehicleState = vehicleStateRepository.createVehicleState(getTestVehicleStateModel())

            val expectedVehicleStateModel = getTestVehicleStateModel()
            expectedVehicleStateModel.setIdsOfRelatedRepositoryEntities(savedVehicleState!!)

            savedVehicleState shouldBe expectedVehicleStateModel
        }

        "UpdateVehicleState should work" {

            val oldVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity())

            val newVehicleState = getTestVehicleStateModel().copy(stateId = oldVehicleStateEntity.stateId)

            val updatedVehicleState = vehicleStateRepository.updateVehicleState(newVehicleState)!!

            val expectedVehicleStateModel = getTestVehicleStateModel()
            expectedVehicleStateModel.setIdsOfRelatedRepositoryEntities(updatedVehicleState)

            updatedVehicleState shouldBe expectedVehicleStateModel
        }

        "DeleteVehicleState should work" {

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity())

            vehicleStateRepository.deleteVehicleState(savedVehicleStateEntity.stateId!!)

            vehicleStateEntityRepository.existsById(savedVehicleStateEntity.stateId!!) shouldBe false
        }
    }

    private fun VehicleState.setIdsOfRelatedRepositoryEntities(savedVehicleState: VehicleState) {

        stateId = savedVehicleState.stateId

        val sortedDataTypes = vehicleStateDataTypes.sortedBy { dataType -> dataType.predicateFieldProviderName }
        val sortedDataTypeEntities = savedVehicleState.vehicleStateDataTypes
                .sortedBy { dataType -> dataType::class.simpleName }

        sortedDataTypes.zip(sortedDataTypeEntities).forEach { (dataTypeWithoutID, dataTypeWithId) ->
            dataTypeWithoutID.dataTypeId = dataTypeWithId.dataTypeId
        }
    }
}
