package de.unia.se.teamcq.vehiclestate.entity

import de.unia.se.teamcq.TestUtils.getTestFleetReferenceEntity
import de.unia.se.teamcq.TestUtils.getTestFleetReferenceEntityTwo
import de.unia.se.teamcq.TestUtils.getTestFleetReferenceModel
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationRuleModel
import de.unia.se.teamcq.TestUtils.getTestUserEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateModel
import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleEntityRepository
import de.unia.se.teamcq.user.entity.IUserEntityRepository
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.Instant

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class VehicleStateRepositoryTest : StringSpec() {

    @Autowired
    private lateinit var vehicleStateEntityRepository: IVehicleStateEntityRepository

    @Autowired
    private lateinit var fleetReferenceEntityRepository: IFleetReferenceEntityRepository

    @Autowired
    private lateinit var vehicleStateRepository: VehicleStateRepository

    @Autowired
    private lateinit var userEntityRepository: IUserEntityRepository

    @Autowired
    private lateinit var notificationRuleEntityRepository: INotificationRuleEntityRepository

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

            actualVehicleState.created shouldBe expectedVehicleStateModel.created
            actualVehicleState shouldBe expectedVehicleStateModel
        }

        "CreateVehicleStates should work" {

            val savedVehicleStates = vehicleStateRepository.createVehicleStates(
                    listOf(getTestVehicleStateModel())
            )
            val savedVehicleState = savedVehicleStates.first()

            val expectedVehicleStateModel = getTestVehicleStateModel()
            expectedVehicleStateModel.setIdsOfRelatedRepositoryEntities(savedVehicleState)

            savedVehicleState shouldBe expectedVehicleStateModel
        }

        "DeleteVehicleState should work" {

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity())

            vehicleStateRepository.deleteVehicleState(savedVehicleStateEntity.stateId!!)

            vehicleStateEntityRepository.existsById(savedVehicleStateEntity.stateId!!) shouldBe false
        }

        "GetAllFleetReferences should work" {

            val fleetReferenceEntityA = getTestFleetReferenceEntity()
            val fleetReferenceEntityB = getTestFleetReferenceEntityTwo()

            fleetReferenceEntityRepository.save(fleetReferenceEntityA)
            fleetReferenceEntityRepository.save(fleetReferenceEntityB)

            val allVehicleStates = vehicleStateRepository.getAllFleetReferences()

            allVehicleStates.size shouldBeGreaterThanOrEqual 2

            allVehicleStates.shouldContain(getTestFleetReferenceModel())
        }

        "GetUnprocessedVehicleStateForRule should work" {

            userEntityRepository.save(getTestUserEntity())

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity().copy(
                    created = Timestamp.from(Instant.now())
            ))
            val savedNotificationRuleEntity = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())

            val unprocessedVehicleStates = vehicleStateRepository
                    .getUnprocessedVehicleStateForRule(getTestNotificationRuleModel().copy(
                            ruleId = savedNotificationRuleEntity.ruleId
                    ))

            val foundVehicleState = unprocessedVehicleStates.any { vehicleState ->
                vehicleState.stateId == savedVehicleStateEntity.stateId
            }

            foundVehicleState shouldBe true
        }

        "MarkVehicleStateAsProcessedByRule should work" {

            userEntityRepository.save(getTestUserEntity())

            val savedVehicleStateEntity = vehicleStateEntityRepository.save(getTestVehicleStateEntity().copy(
                    created = Timestamp.from(Instant.now())
            ))
            val vehicleStateModelWithSavedStateId = getTestVehicleStateModel()
                    .copy(stateId = savedVehicleStateEntity.stateId)

            val savedNotificationRuleEntity = notificationRuleEntityRepository.save(getTestNotificationRuleEntity())
            val notificationRuleModelWithSavedId = getTestNotificationRuleModel().copy(
                    ruleId = savedNotificationRuleEntity.ruleId
            )

            val unprocessedVehicleStates = vehicleStateRepository
                    .getUnprocessedVehicleStateForRule(notificationRuleModelWithSavedId)

            val foundVehicleState = unprocessedVehicleStates.any { vehicleState ->
                vehicleState.stateId == savedVehicleStateEntity.stateId
            }

            foundVehicleState shouldBe true

            vehicleStateRepository.markVehicleStateAsProcessedByRule(
                    notificationRuleModelWithSavedId,
                    listOf(vehicleStateModelWithSavedStateId))

            val stillUnprocessedVehicleStates = vehicleStateRepository
                    .getUnprocessedVehicleStateForRule(notificationRuleModelWithSavedId)

            val foundVehicleStateAfter = stillUnprocessedVehicleStates.any { vehicleState ->
                vehicleState.stateId == savedVehicleStateEntity.stateId
            }

            foundVehicleStateAfter shouldBe false
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
