package de.unia.se.teamcq

import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldDto
import de.unia.se.teamcq.ruleevaluation.dto.PredicateFieldProviderDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionCompositeDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionPredicateDto
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionCompositeEntity
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionPredicateEntity
import de.unia.se.teamcq.ruleevaluation.model.*
import de.unia.se.teamcq.rulemanagement.dto.NotificationRuleDto
import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.rulemanagement.model.NotificationRuleBuilder
import de.unia.se.teamcq.security.JwtConfig
import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.user.dto.UserSettingsDto
import de.unia.se.teamcq.user.entity.UserEntity
import de.unia.se.teamcq.user.entity.UserSettingsEntity
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.user.model.UserNotificationType
import de.unia.se.teamcq.user.model.UserSettings
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntity
import de.unia.se.teamcq.vehiclestate.model.*
import org.springframework.http.HttpHeaders
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

object TestUtils {

    fun buildMockMvc(webApplicationContext: WebApplicationContext): MockMvc {
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    fun getTestNotificationRuleModel(): NotificationRule {
        return NotificationRuleBuilder()
                .withId(0)
                .withName("rule_name")
                .withOwner(getTestUserModel())
                .withDescription("description")
                .build()
    }

    fun getTestNotificationRuleDto(): NotificationRuleDto {
        return NotificationRuleDto(
                ruleId = 0,
                name = "rule_name",
                owner = getTestUserDto(),
                description = "description"
        )
    }

    fun getTestNotificationRuleEntity(): NotificationRuleEntity {
        return NotificationRuleEntity(
                ruleId = 0,
                name = "rule_name",
                owner = getTestUserEntity(),
                description = "description"
        )
    }

    fun getTestUserModel(): User {
        return User(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettings(UserNotificationType.EMAIL)
        )
    }

    fun getTestUserDto(): UserDto {
        return UserDto(
                name = "Max Mustermann",
                mailAddress = "test@example.de",
                cellPhoneNumber = "1",
                userSettings = UserSettingsDto(UserNotificationType.EMAIL)
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
                "predicateFieldProviderName",
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
                "predicateFieldProviderName"
        )
    }

    fun getTestPredicateFieldProviderModel(): IPredicateFieldProvider {
        return VehicleStateDataTypeBattery()
    }

    fun getTestPredicateFieldProviderDto(): PredicateFieldProviderDto {
        return PredicateFieldProviderDto("Battery", listOf(
                PredicateFieldDto("charge", FieldDataType.DECIMAL, EvaluationStrategies.STANDARD),
                PredicateFieldDto("voltage", FieldDataType.DECIMAL, EvaluationStrategies.STANDARD),
                PredicateFieldDto("status", FieldDataType.TEXT, EvaluationStrategies.TEXT)
        ))
    }

    fun getTestPredicateFieldModel(): PredicateField {
        return PredicateField("charge", FieldDataType.DECIMAL, EvaluationStrategies.STANDARD)
    }

    fun getTestPredicateFieldDto(): PredicateFieldDto {
        return PredicateFieldDto("charge", FieldDataType.DECIMAL, EvaluationStrategies.STANDARD)
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

    fun prepareAccessTokenHeader(jwtConfig: JwtConfig, accessToken: String): HttpHeaders {
        val httpHeader = HttpHeaders()
        httpHeader.add(jwtConfig.header, jwtConfig.prefix + accessToken)
        return httpHeader
    }
}
