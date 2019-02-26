package de.unia.se.teamcq.notificationmanagement

import de.unia.se.teamcq.TestUtils.getTestNotificationRuleEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeEntity
import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateEntity
import de.unia.se.teamcq.TestUtils.getTestUserModel
import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.notificationmanagement.service.INotificationMESAdapter
import de.unia.se.teamcq.notificationmanagement.service.INotificationService
import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import de.unia.se.teamcq.ruleevaluation.service.IEvaluationService
import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleEntityRepository
import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.security.service.AuthenticationTokenAdapter
import de.unia.se.teamcq.user.entity.IUserRepository
import de.unia.se.teamcq.vehiclestate.entity.IVehicleStateEntityRepository
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.vehiclestate.service.IVehicleStateService
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils
import java.sql.Timestamp
import java.util.Date

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProcessingPipelineIntegrationTest : StringSpec() {

    @Autowired
    private lateinit var authenticationTokenAdapter: AuthenticationTokenAdapter

    @Autowired
    private lateinit var vehicleStateEntityRepository: IVehicleStateEntityRepository

    @Autowired
    private lateinit var evaluationService: IEvaluationService

    @Autowired
    private lateinit var userRepository: IUserRepository

    @Autowired
    private lateinit var notificationService: INotificationService

    @Autowired
    private lateinit var vehicleStateService: IVehicleStateService

    @Autowired
    private lateinit var notificationMESAdapter: INotificationMESAdapter

    @Autowired
    private lateinit var notificationRuleEntityRepository: INotificationRuleEntityRepository

    @Autowired
    private lateinit var notificationRuleRepository: INotificationRuleRepository

    init {
        MockKAnnotations.init(this)

        "Import and process new Vehicles when API Mock is running".config(enabled = false) {

            // Setup
            val savedNotificationRule = getTestRuleMatchingAllStates()
            val ruleId = savedNotificationRule!!.ruleId!!
            setRequiredEnvironmentVariables()

            // Import VehicleStates and fill in values required for test but missing in Mock answer
            vehicleStateService.importNewVehicleData()
            fillMissingValuesInImportedStatesFromMock()

            // Get VehicleStates to process
            val vehicleStatesToProcess = vehicleStateService
                    .getUnprocessedVehicleStateForRule(savedNotificationRule)
            vehicleStatesToProcess.size shouldBeGreaterThanOrEqual 5000
            val someTestStatesToProcess = vehicleStatesToProcess.take(10)

            // Process VehicleStates
            val vehicleStateMatches = someTestStatesToProcess.filter { vehicleState ->
                evaluationService.checkCondition(savedNotificationRule.condition!!, vehicleState)
            }.toSet()
            vehicleStateMatches.size shouldBe 10

            // Store results from processing
            val notificationData = NotificationData(savedNotificationRule, vehicleStateMatches)
            notificationService.storeNotificationData(notificationData)
            vehicleStateService.markVehicleStateAsProcessedByRule(savedNotificationRule, vehicleStatesToProcess)
            assertDbStateAfterProcessingBeforeSending(ruleId)

            // Send notifications as necessary and update DB
            notificationService.sendNotificationForRuleIfNecessary(savedNotificationRule)
            assertDbStateAfterSending(ruleId)
        }
    }

    private fun assertDbStateAfterSending(ruleId: Long) {
        val notificationRuleEntityWithoutMatches = notificationRuleEntityRepository.getOne(ruleId)
        notificationRuleEntityWithoutMatches.matchedVehicleStatesNotYetSent!!.size shouldBe 0
        notificationRuleEntityWithoutMatches.processedVehicleStates!!.size shouldBeGreaterThanOrEqual 5000
    }

    private fun assertDbStateAfterProcessingBeforeSending(ruleId: Long): Pair<Long, NotificationRuleEntity> {
        val notificationRuleEntityWithMatches = notificationRuleEntityRepository.getOne(ruleId)

        notificationRuleEntityWithMatches.matchedVehicleStatesNotYetSent!!.size shouldBeGreaterThanOrEqual 10
        notificationRuleEntityWithMatches.processedVehicleStates!!.size shouldBeGreaterThanOrEqual 5000
        return Pair(ruleId, notificationRuleEntityWithMatches)
    }

    private fun fillMissingValuesInImportedStatesFromMock() {

        val vehicleStateEntitiesWithCreatedValue = vehicleStateEntityRepository.findAll()
                .map { vehicleState ->
                    vehicleState.created = Timestamp(Date().time)
                    val dataTypeBattery = vehicleState.vehicleStateDataTypes.first { dataType ->
                        dataType is VehicleStateDataTypeBatteryEntity
                    }
                    dataTypeBattery as VehicleStateDataTypeBatteryEntity
                    dataTypeBattery.charge = 0.1

                    vehicleState
                }

        vehicleStateEntityRepository.saveAll(vehicleStateEntitiesWithCreatedValue)
    }

    private fun setRequiredEnvironmentVariables() {
        ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationUsername", "admin")
        ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationPassword", "fd123!")
        ReflectionTestUtils.setField(notificationMESAdapter, "disableNotifications", true)
    }

    private fun getTestRuleMatchingAllStates(): NotificationRule? {
        val notificationRuleEntity = getTestNotificationRuleEntity().apply {
            condition = getTestRuleConditionCompositeEntity().apply {
                this.logicalConnective = LogicalConnectiveType.ANY
                this.subConditions = listOf(
                        getTestRuleConditionPredicateEntity().apply {
                            this.comparisonValue = "0.5"
                        }
                )
            }
            lastUpdate = Timestamp(0)
        }
        userRepository.createOrSaveUser(getTestUserModel())
        val savedNotificationRuleEntity = notificationRuleEntityRepository.save(notificationRuleEntity)
        return notificationRuleRepository.getNotificationRule(
                savedNotificationRuleEntity.ruleId!!
        )
    }
}
