package de.unia.se.teamcq

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
import de.unia.se.teamcq.user.model.UserNotificationType
import de.unia.se.teamcq.user.model.UserSettings
import de.unia.se.teamcq.vehiclestate.dto.FleetReferenceDto
import de.unia.se.teamcq.vehiclestate.entity.FleetReferenceEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleReferenceEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntity
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import de.unia.se.teamcq.vehiclestate.model.VehicleReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeContract
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeEngine
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeFuel
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeMileage
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeService
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.springframework.http.HttpHeaders
import org.springframework.scheduling.support.CronTrigger
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.Date

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
                ownerAsAdditionalRecipient = true
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
                ownerAsAdditionalRecipient = true
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
                ownerAsAdditionalRecipient = true
        )
    }

    fun getTestUserModel(): User {
        return User(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettings(0, UserNotificationType.EMAIL)
        )
    }

    fun getTestUserDto(): UserDto {
        return UserDto(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettingsDto(0, UserNotificationType.EMAIL)
        )
    }

    fun getTestUserEntity(): UserEntity {
        return UserEntity(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettingsEntity(0, UserNotificationType.EMAIL),
                notificationRules = emptySet())
    }

    fun getTestVehicleStateDataTypeBatteryModel(): VehicleStateDataTypeBattery {
        return VehicleStateDataTypeBattery(0.5, 0.7, "Healthy", 10)
    }

    fun getTestVehicleStateDataTypeContractModel(): VehicleStateDataTypeContract {
        return VehicleStateDataTypeContract(
                10,
                listOf("1002A", "1008B"),
                9,
                11
        )
    }

    fun getTestVehicleStateDataTypeEngineModel(): VehicleStateDataTypeEngine {
        return VehicleStateDataTypeEngine(120, 120, "Gas", 12)
    }

    fun getTestVehicleStateDataTypeFuelModel(): VehicleStateDataTypeFuel {
        return VehicleStateDataTypeFuel(0.4, 50, 1000, 13)
    }

    fun getTestVehicleStateDataTypeMileageModel(): VehicleStateDataTypeMileage {
        return VehicleStateDataTypeMileage(10000, 5000, 1000, 14)
    }

    fun getTestVehicleStateDataTypeServiceModel(): VehicleStateDataTypeService {
        return VehicleStateDataTypeService(Date(1547650098), "Fine", "Healthy", 15)
    }

    fun getTestVehicleStateModel(): VehicleState {
        return VehicleState(
                0,
                getTestVehicleReferenceModel(),
                setOf(
                        getTestVehicleStateDataTypeBatteryModel(),
                        getTestVehicleStateDataTypeContractModel(),
                        getTestVehicleStateDataTypeEngineModel(),
                        getTestVehicleStateDataTypeFuelModel(),
                        getTestVehicleStateDataTypeMileageModel(),
                        getTestVehicleStateDataTypeServiceModel()
                )
        )
    }

    fun getTestVehicleStateEnity(): VehicleStateEntity {
        return VehicleStateEntity(
                0,
                getTestVehicleReferenceEntity()
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
        return RuleConditionPredicate(0, "Battery", "charge", ComparisonType.LESSER_THAN_OR_EQUAL_TO, "0.1")
    }

    fun getTestRuleConditionPredicateDto(): RuleConditionPredicateDto {
        return RuleConditionPredicateDto(0, "Battery", "charge", ComparisonType.LESSER_THAN_OR_EQUAL_TO, "0.1")
    }

    fun getTestRuleConditionPredicateEntity(): RuleConditionPredicateEntity {
        return RuleConditionPredicateEntity(0, "Battery", "charge", ComparisonType.LESSER_THAN_OR_EQUAL_TO, "0.1")
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
        return AggregatorScheduled(0, CronTrigger("0 0 10 * * MON"))
    }

    fun getTestAggregatorScheduledDto(): AggregatorScheduledDto {
        return AggregatorScheduledDto(0, "0 0 10 * * MON")
    }

    fun getTestAggregatorScheduledEntity(): AggregatorScheduledEntity {
        return AggregatorScheduledEntity(0, "0 0 10 * * MON")
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

    fun <T> testEqualAndHashCode(generateObject: () -> T, vararg modifiers: (T) -> Unit) {

        generateObject() shouldBe generateObject()

        modifiers.forEach { modifier ->
            val objectToModify = generateObject()
            modifier(objectToModify)
            generateObject() shouldNotBe objectToModify
        }

        generateObject().hashCode() shouldBe generateObject().hashCode()
    }

    fun getTestFleetReferenceModel(): FleetReference {
        return FleetReference(
                fleetId = "UUID123"
        )
    }

    fun getTestFleetReferenceEntity(): FleetReferenceEntity {
        return FleetReferenceEntity(
                fleetId = "UUID123"
        )
    }

    fun getTestFleetReferenceDto(): FleetReferenceDto {
        return FleetReferenceDto(
                fleetId = "UUID123"
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

    fun prepareAccessTokenHeader(jwtConfig: JwtConfig, accessToken: String): HttpHeaders {
        val httpHeader = HttpHeaders()
        httpHeader.add(jwtConfig.header, jwtConfig.prefix + accessToken)
        return httpHeader
    }
}
