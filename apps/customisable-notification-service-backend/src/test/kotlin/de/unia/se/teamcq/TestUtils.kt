package de.unia.se.teamcq

import de.bmw.vss.model.Battery
import de.bmw.vss.model.ConditionBasedService
import de.bmw.vss.model.Contract
import de.bmw.vss.model.Engine
import de.bmw.vss.model.Fuel
import de.bmw.vss.model.Mileage
import de.bmw.vss.model.ServiceStatus
import de.bmw.vss.model.Vehicle
import de.unia.se.teamcq.notificationmanagement.dto.AggregatorCountingDto
import de.unia.se.teamcq.notificationmanagement.dto.AggregatorDto
import de.unia.se.teamcq.notificationmanagement.dto.AggregatorImmediateDto
import de.unia.se.teamcq.notificationmanagement.dto.AggregatorScheduledDto
import de.unia.se.teamcq.notificationmanagement.dto.RecipientDto
import de.unia.se.teamcq.notificationmanagement.dto.RecipientMailDto
import de.unia.se.teamcq.notificationmanagement.dto.RecipientSmsDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorCountingEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorImmediateEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorScheduledEntity
import de.unia.se.teamcq.notificationmanagement.entity.RecipientEntity
import de.unia.se.teamcq.notificationmanagement.entity.RecipientMailEntity
import de.unia.se.teamcq.notificationmanagement.entity.RecipientSmsEntity
import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.AggregatorCounting
import de.unia.se.teamcq.notificationmanagement.model.AggregatorImmediate
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import de.unia.se.teamcq.notificationmanagement.model.RecipientMail
import de.unia.se.teamcq.notificationmanagement.model.RecipientSms
import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldDto
import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldProviderDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionCompositeDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionPredicateDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionCompositeEntity
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionEntity
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionPredicateEntity
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import de.unia.se.teamcq.ruleevaluation.model.EvaluationStrategies
import de.unia.se.teamcq.ruleevaluation.model.FieldDataType
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import de.unia.se.teamcq.ruleevaluation.model.PredicateField
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionComposite
import de.unia.se.teamcq.ruleevaluation.model.RuleConditionPredicate
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.security.JwtConfig
import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.dto.UserSettingsDto
import de.unia.se.teamcq.user.entity.UserEntity
import de.unia.se.teamcq.user.entity.UserSettingsEntity
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.model.UserLocale
import de.unia.se.teamcq.user.model.UserNotificationType
import de.unia.se.teamcq.user.model.UserSettings
import de.unia.se.teamcq.vehiclestate.dto.FleetReferenceDto
import de.unia.se.teamcq.vehiclestate.entity.FleetReferenceEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleReferenceEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeContractEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEngineEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeFuelEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeMileageEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeServiceEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntity
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeContract
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeEngine
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeFuel
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeMileage
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeService
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.quartz.CronExpression
import org.springframework.http.HttpHeaders
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.lang.IllegalArgumentException
import java.sql.Date
import java.sql.Timestamp
import java.util.GregorianCalendar
import java.util.UUID
import javax.xml.bind.DatatypeConverter

object TestUtils {

    fun buildMockMvc(webApplicationContext: WebApplicationContext): MockMvc {
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    fun getTestNotificationRuleModel(): NotificationRule {
        return NotificationRule(
                ruleId = 0,
                name = "rule_name",
                owner = getTestUserModel(),
                description = "description",
                condition = getTestRuleConditionModel(),
                aggregator = getTestAggregatorModel(),
                recipients = getTestRecipientModels(),
                ownerAsAdditionalRecipient = true,
                affectedFleets = getTestFleetReferenceModels(),
                affectingAllApplicableFleets = false,
                lastUpdate = Timestamp(1547650000)
        )
    }

    fun getTestNotificationRuleDto(): NotificationRuleDto {
        return NotificationRuleDto(
                ruleId = 0,
                name = "rule_name",
                owner = getTestUserDto(),
                description = "description",
                condition = getTestRuleConditionDto(),
                aggregator = getTestAggregatorDto(),
                recipients = getTestRecipientDtos(),
                ownerAsAdditionalRecipient = true,
                affectedFleets = getTestFleetReferenceDtos(),
                affectingAllApplicableFleets = false
        )
    }

    fun getTestNotificationRuleEntity(): NotificationRuleEntity {
        return NotificationRuleEntity(
                ruleId = 0,
                name = "rule_name",
                owner = getTestUserEntity(),
                description = "description",
                condition = getTestRuleConditionEntity(),
                aggregator = getTestAggregatorEntity(),
                recipients = getTestRecipientEntities(),
                ownerAsAdditionalRecipient = true,
                affectedFleets = getTestFleetReferenceEntities(),
                affectingAllApplicableFleets = false,
                lastUpdate = Timestamp(1547650000)
        )
    }

    fun getTestUserModel(): User {
        return User(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettings(0, UserNotificationType.EMAIL, UserLocale.EN)
        )
    }

    fun getTestUserDto(): UserDto {
        return UserDto(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettingsDto(0, UserNotificationType.EMAIL, UserLocale.EN)
        )
    }

    fun getTestUserEntity(): UserEntity {
        return UserEntity(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettingsEntity(0, UserNotificationType.EMAIL, UserLocale.EN),
                notificationRules = emptySet())
    }

    fun getTestVehicleStateDataTypeBatteryModel(): VehicleStateDataTypeBattery {
        return VehicleStateDataTypeBattery(0.5.toFloat().toDouble(), 0.7.toFloat().toDouble(), "OK", 10, 5)
    }

    fun getTestVehicleStateDataTypeBatteryEntity(): VehicleStateDataTypeBatteryEntity {
        return VehicleStateDataTypeBatteryEntity(0.5.toFloat().toDouble(), 0.7.toFloat().toDouble(), "OK", 10, 5)
    }

    fun getTestVehicleStateDataTypeContractModel(): VehicleStateDataTypeContract {
        return VehicleStateDataTypeContract(
                10,
                Date(1547650098),
                9,
                11,
                Date(1547650098),
                5
        )
    }

    fun getTestVehicleStateDataTypeContractEntity(): VehicleStateDataTypeContractEntity {
        return VehicleStateDataTypeContractEntity(
                10,
                Date(1547650098),
                9,
                11,
                Date(1547650098),
                5
        )
    }

    fun getTestVehicleStateDataTypeEngineModel(): VehicleStateDataTypeEngine {
        return VehicleStateDataTypeEngine(120, 120, "Gas", "Some")
    }

    fun getTestVehicleStateDataTypeEngineEntity(): VehicleStateDataTypeEngineEntity {
        return VehicleStateDataTypeEngineEntity(120, 120, "Gas", "Some")
    }

    fun getTestVehicleStateDataTypeFuelModel(): VehicleStateDataTypeFuel {
        return VehicleStateDataTypeFuel(0.4, 50, 100.0, 13)
    }

    fun getTestVehicleStateDataTypeFuelEntity(): VehicleStateDataTypeFuelEntity {
        return VehicleStateDataTypeFuelEntity(0.4, 50, 100.0, 13)
    }

    fun getTestVehicleStateDataTypeMileageModel(): VehicleStateDataTypeMileage {
        return VehicleStateDataTypeMileage(10000, 5000, 1000, 14, 5, 9, "test")
    }

    fun getTestVehicleStateDataTypeMileageEntity(): VehicleStateDataTypeMileageEntity {
        return VehicleStateDataTypeMileageEntity(10000, 5000, 1000, 14, 5, 9, "test")
    }

    fun getTestVehicleStateDataTypeServiceModel(): VehicleStateDataTypeService {
        return VehicleStateDataTypeService("10.12.208", "OK", 20, 15)
    }

    fun getTestVehicleStateDataTypeServiceEntity(): VehicleStateDataTypeServiceEntity {
        return VehicleStateDataTypeServiceEntity("10.12.208", "OK", 20, 15)
    }

    fun getTestVehicleStateModel(): VehicleState {
        return VehicleState(
                0,
                getTestVehicleReferenceModel(),
                getTestVehicleStateDataTypeModels(),
                Timestamp(1547650098)
        )
    }

    fun getTestVehicleStateDataTypeModels(): Set<VehicleStateDataType> {
        return setOf(
                getTestVehicleStateDataTypeBatteryModel(),
                getTestVehicleStateDataTypeContractModel(),
                getTestVehicleStateDataTypeEngineModel(),
                getTestVehicleStateDataTypeFuelModel(),
                getTestVehicleStateDataTypeMileageModel(),
                getTestVehicleStateDataTypeServiceModel()
        )
    }

    fun getTestVehicleStateDataTypeEntities(): Set<VehicleStateDataTypeEntity> {
        return setOf(
                getTestVehicleStateDataTypeBatteryEntity(),
                getTestVehicleStateDataTypeContractEntity(),
                getTestVehicleStateDataTypeEngineEntity(),
                getTestVehicleStateDataTypeFuelEntity(),
                getTestVehicleStateDataTypeMileageEntity(),
                getTestVehicleStateDataTypeServiceEntity()
        )
    }

    inline fun <reified VehicleStateDatum : VehicleStateDataType> VehicleState.updateVehicleStateDataTypeField(
        updater: (VehicleStateDatum) -> Unit
    ): VehicleState {
        this.vehicleStateDataTypes.find { vehicleStateDataType ->
            vehicleStateDataType is VehicleStateDatum
        }?.apply {
            when (this) {
                is VehicleStateDatum -> updater(this)
                else -> throw IllegalArgumentException(
                        "Passed data type in test did not fit required ${VehicleStateDatum::class.java} type."
                )
            }
        }
        return this
    }

    fun getTestVehicleStateEntity(): VehicleStateEntity {
        return VehicleStateEntity(
                0,

                getTestVehicleReferenceEntity(),
                getTestVehicleStateDataTypeEntities(),
                Timestamp(1547650098)
        )
    }

    fun getTestPredicateFieldProviders(): Set<IPredicateFieldProvider> {
        return setOf(
                VehicleStateDataTypeBattery(),
                VehicleStateDataTypeEngine(),
                VehicleStateDataTypeService()
        )
    }

    fun getTestPredicateFieldProviderModel(): IPredicateFieldProvider {
        return VehicleStateDataTypeBattery()
    }

    fun getTestPredicateFieldProviderDto(): PredicateFieldProviderDto {
        return PredicateFieldProviderDto("Battery", listOf(
                PredicateFieldDto("charge", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
                PredicateFieldDto("voltage", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC),
                PredicateFieldDto("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
        ))
    }

    fun getTestPredicateFieldModel(): PredicateField {
        return PredicateField("charge", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC)
    }

    fun getTestPredicateFieldDto(): PredicateFieldDto {
        return PredicateFieldDto("charge", FieldDataType.DECIMAL, EvaluationStrategies.NUMERIC)
    }

    fun getTestRuleConditionPredicateModel(): RuleConditionPredicate {
        return RuleConditionPredicate(0, "Battery", "charge", ComparisonType.LESS_THAN_OR_EQUAL_TO, "0.1")
    }

    fun getTestRuleConditionPredicateDto(): RuleConditionPredicateDto {
        return RuleConditionPredicateDto(0, "Battery", "charge", ComparisonType.LESS_THAN_OR_EQUAL_TO, "0.1")
    }

    fun getTestRuleConditionPredicateEntity(): RuleConditionPredicateEntity {
        return RuleConditionPredicateEntity(0, "Battery", "charge", ComparisonType.LESS_THAN_OR_EQUAL_TO, "0.1")
    }

    fun getTestRuleConditionCompositeModel(): RuleConditionComposite {
        return RuleConditionComposite(
                0,
                LogicalConnectiveType.ALL,
                listOf(
                        getTestRuleConditionPredicateModel()
                )
        )
    }

    fun getTestRuleConditionCompositeDto(): RuleConditionCompositeDto {
        return RuleConditionCompositeDto(
                0,
                LogicalConnectiveType.ALL,
                listOf(
                        getTestRuleConditionPredicateDto()
                )
        )
    }

    fun getTestRuleConditionCompositeEntity(): RuleConditionCompositeEntity {
        return RuleConditionCompositeEntity(
                0,
                LogicalConnectiveType.ALL,
                listOf(
                        getTestRuleConditionPredicateEntity()
                )
        )
    }

    fun getTestRuleConditionModel(): RuleCondition {
        return getTestRuleConditionCompositeModel()
    }

    fun getTestRuleConditionDto(): RuleConditionDto {
        return getTestRuleConditionCompositeDto()
    }

    fun getTestRuleConditionEntity(): RuleConditionEntity {
        return getTestRuleConditionCompositeEntity()
    }

    fun getTestRuleConditionModelWithGreaterDepth(): RuleCondition {
        return RuleConditionComposite(
                0,
                LogicalConnectiveType.ANY,
                listOf(
                        getTestRuleConditionModel(),
                        getTestRuleConditionModel()
                )
        )
    }

    fun getTestRuleConditionDtoWithGreaterDepth(): RuleConditionDto {
        return RuleConditionCompositeDto(
                0,
                LogicalConnectiveType.ANY,
                listOf(
                        getTestRuleConditionDto(),
                        getTestRuleConditionDto()
                )
        )
    }

    fun getTestRuleConditionEntityWithGreaterDepth(): RuleConditionEntity {
        return RuleConditionCompositeEntity(
                0,
                LogicalConnectiveType.ANY,
                listOf(
                        getTestRuleConditionEntity(),
                        getTestRuleConditionEntity()
                )
        )
    }

    fun getAggregatorImmediateModel(): AggregatorImmediate {
        return AggregatorImmediate(0)
    }

    fun getTestAggregatorImmediateDto(): AggregatorImmediateDto {
        return AggregatorImmediateDto(0)
    }

    fun getTestAggregatorImmediateEntity(): AggregatorImmediateEntity {
        return AggregatorImmediateEntity(0)
    }

    fun getTestAggregatorCountingModel(): AggregatorCounting {
        return AggregatorCounting(0, 10)
    }

    fun getTestAggregatorCountingDto(): AggregatorCountingDto {
        return AggregatorCountingDto(0, 10)
    }

    fun getTestAggregatorCountingEntity(): AggregatorCountingEntity {
        return AggregatorCountingEntity(0, 10)
    }

    fun getTestAggregatorScheduledModel(): AggregatorScheduled {
        return AggregatorScheduled(0, CronExpression("0 15 10 ? * TUE"))
    }

    fun getTestAggregatorScheduledDto(): AggregatorScheduledDto {
        return AggregatorScheduledDto(0, "0 15 10 ? * TUE")
    }

    fun getTestAggregatorScheduledEntity(): AggregatorScheduledEntity {
        return AggregatorScheduledEntity(0, "0 15 10 ? * TUE")
    }

    fun getTestAggregatorModel(): Aggregator {
        return getTestAggregatorScheduledModel()
    }

    fun getTestAggregatorDto(): AggregatorDto {
        return getTestAggregatorScheduledDto()
    }

    fun getTestAggregatorEntity(): AggregatorEntity {
        return getTestAggregatorScheduledEntity()
    }

    fun getTestVehicleStateDataTypeEntity(): VehicleStateDataTypeEntity {
        return getTestVehicleStateDataTypeBatteryEntity()
    }

    fun getTestVehicleStateDataTypeModel(): VehicleStateDataType {
        return getTestVehicleStateDataTypeBatteryModel()
    }

    fun getTestRecipientDto(): RecipientDto {
        return getTestRecipientMailDto()
    }

    fun getTestRecipientMailDto(): RecipientMailDto {
        return RecipientMailDto(0, "test@example.com")
    }

    fun getTestRecipientSmsDto(): RecipientSmsDto {
        return RecipientSmsDto(0, "12345678")
    }

    fun getTestRecipientModel(): Recipient {
        return getTestRecipientMailModel()
    }

    fun getTestRecipientMailModel(): RecipientMail {
        return RecipientMail(0, "test@example.com")
    }

    fun getTestRecipientSmsModel(): RecipientSms {
        return RecipientSms(0, "12345678")
    }

    fun getTestRecipientEntity(): RecipientEntity {
        return getTestRecipientMailEntity()
    }

    fun getTestRecipientMailEntity(): RecipientMailEntity {
        return RecipientMailEntity(0, "test@example.com")
    }

    fun getTestRecipientSmsEntity(): RecipientSmsEntity {
        return RecipientSmsEntity(0, "12345678")
    }

    fun getTestRecipientEntities(): List<RecipientEntity> {
        return listOf(getTestRecipientSmsEntity(), getTestRecipientMailEntity())
    }

    fun getTestRecipientDtos(): List<RecipientDto> {
        return listOf(getTestRecipientSmsDto(), getTestRecipientMailDto())
    }

    fun getTestRecipientModels(): List<Recipient> {
        return listOf(getTestRecipientSmsModel(), getTestRecipientMailModel())
    }

    fun getTestFleetReferenceModel(): FleetReference {
        return FleetReference(
                fleetId = "cccccccc-0000-ffff-0000-000000000099",
                carParkId = "cccccccc-0000-cccc-0000-000000000099"
        )
    }

    fun getTestFleetReferenceEntity(): FleetReferenceEntity {
        return FleetReferenceEntity(
                fleetId = "cccccccc-0000-ffff-0000-000000000099",
                carParkId = "cccccccc-0000-cccc-0000-000000000099"
        )
    }

    fun getTestFleetReferenceDto(): FleetReferenceDto {
        return FleetReferenceDto(
                fleetId = "cccccccc-0000-ffff-0000-000000000099",
                carParkId = "cccccccc-0000-cccc-0000-000000000099"
        )
    }

    fun getTestVehicleReferenceModel(): VehicleReference {
        return VehicleReference(
                vin = "UUID456",
                fleetReference = getTestFleetReferenceModel()
        )
    }

    fun getTestVehicleReferenceEntity(): VehicleReferenceEntity {
        return VehicleReferenceEntity(
                vin = "UUID456",
                fleetReference = getTestFleetReferenceEntity()
        )
    }

    fun getTestFleetReferenceDtoTwo(): FleetReferenceDto {
        return getTestFleetReferenceDto().copy(fleetId = "cccccccc-0000-ffff-0000-000000000098")
    }

    fun getTestFleetReferenceDtos(): List<FleetReferenceDto> {
        return listOf(
                getTestFleetReferenceDto(),
                getTestFleetReferenceDtoTwo()
        )
    }

    fun getTestFleetReferenceModelTwo(): FleetReference {
        return getTestFleetReferenceModel().copy(fleetId = "UUID234")
    }

    fun getTestFleetReferenceModels(): List<FleetReference> {
        return listOf(
                getTestFleetReferenceModel(),
                getTestFleetReferenceModelTwo()
        )
    }

    fun getTestFleetReferenceEntityTwo(): FleetReferenceEntity {
        return getTestFleetReferenceEntity().copy(fleetId = "UUID234")
    }

    fun getTestFleetReferenceEntities(): List<FleetReferenceEntity> {
        return listOf(
                getTestFleetReferenceEntity(),
                getTestFleetReferenceEntityTwo()
        )
    }

    fun getTestNotificationDataModel(): NotificationData {
        return NotificationData(getTestNotificationRuleModel(), setOf(
                getTestVehicleStateModel(),
                getTestVehicleStateModel().apply { stateId = 1 },
                getTestVehicleStateModel().apply { stateId = 2 })
        )
    }

    fun <T> testEqualAndHashCode(generateObject: () -> T, vararg modifiers: (T) -> Unit) {

        generateObject() shouldBe generateObject()

        modifiers.forEach { modifier ->
            val objectToModify = generateObject()
            modifier(objectToModify)
            generateObject() shouldNotBe objectToModify
        }

        generateObject().hashCode() shouldBe generateObject().hashCode()
    }

    fun prepareAccessTokenHeader(jwtConfig: JwtConfig, accessToken: String): HttpHeaders {
        val httpHeader = HttpHeaders()
        httpHeader.add(jwtConfig.header, jwtConfig.prefix + accessToken)
        return httpHeader
    }

    fun getTestVSSVehicle(): Vehicle {
        val vehicle = Vehicle()
        vehicle.vin = "UUID456"
        vehicle.fleet = UUID.fromString("cccccccc-0000-ffff-0000-000000000099")
        vehicle.carPark = UUID.fromString("cccccccc-0000-cccc-0000-000000000099")

        val battery = Battery()
        battery.levelPercentage = 0.5.toFloat()
        battery.voltage = 0.7.toFloat()
        battery.chargingStatus = "OK"
        vehicle.battery = battery

        val engine = Engine()
        engine.power = 120
        engine.capacity = 120
        engine.fuelType = "Gas"
        vehicle.engine = engine

        val fuel = Fuel()
        fuel.levelPercentage = 0.4
        fuel.levelLiters = 50
        fuel.remainingRange = 1000
        vehicle.fuel = fuel

        val contract = Contract() // FIXME
        vehicle.contract = contract

        val service = ServiceStatus()
        val calendar = GregorianCalendar()
        calendar.time = Date(1547650098)
        service.dueDate = DatatypeConverter.printDateTime(calendar)
        val conditionBasedService = ConditionBasedService()
        conditionBasedService.status = ConditionBasedService.StatusEnum.OK
        service.brakeFluid = conditionBasedService
        service.status = ServiceStatus.StatusEnum.OK
        vehicle.serviceStatus = service

        val mileage = Mileage()
        mileage.current = 10000
        mileage.remaining = 5000
        mileage.reachedPercentage = 1000
        vehicle.mileage = mileage

        return vehicle
    }
}
