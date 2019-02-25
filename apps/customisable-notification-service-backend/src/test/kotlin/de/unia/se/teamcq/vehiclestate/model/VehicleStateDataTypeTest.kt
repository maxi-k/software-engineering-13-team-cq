package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.lang.IllegalArgumentException

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeTest : StringSpec() {

    init {
        "VehicleStateDataTypeBattery should contain relevant predicate fields" {
            val vehicleStateBattery = TestUtils.getTestVehicleStateDataTypeBatteryModel()
            vehicleStateBattery.predicateFields["charge"] shouldNotBe null
            vehicleStateBattery.predicateFields["nonExistent"] shouldBe null
            vehicleStateBattery.retrieveFieldValue("charge") shouldBe vehicleStateBattery.charge
            vehicleStateBattery.retrieveFieldValue("chargingStatus") shouldBe vehicleStateBattery.chargingStatus
            vehicleStateBattery.retrieveFieldValue("remainingChargingHours") shouldBe vehicleStateBattery.remainingChargingHours
            vehicleStateBattery.retrieveFieldValue("voltage") shouldBe vehicleStateBattery.voltage
            vehicleStateBattery.retrieveFieldValue("remainingRange") shouldBe vehicleStateBattery.remainingRange
            shouldThrow<IllegalArgumentException> {
                vehicleStateBattery.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeContract should contain relevant predicate fields" {
            val vehicleStateContract = TestUtils.getTestVehicleStateDataTypeContractModel()
            vehicleStateContract.predicateFields["duePerWeek"] shouldNotBe null
            vehicleStateContract.predicateFields["nonExistent"] shouldBe null
            vehicleStateContract.retrieveFieldValue("startDate") shouldBe vehicleStateContract.startDate
            vehicleStateContract.retrieveFieldValue("endDate") shouldBe vehicleStateContract.endDate
            vehicleStateContract.retrieveFieldValue("startDate") shouldBe vehicleStateContract.startDate
            vehicleStateContract.retrieveFieldValue("startMileage") shouldBe vehicleStateContract.startMileage
            vehicleStateContract.retrieveFieldValue("reachedRuntimePercentage") shouldBe vehicleStateContract.reachedRuntimePercentage
            vehicleStateContract.retrieveFieldValue("remainingDays") shouldBe vehicleStateContract.remainingDays
            shouldThrow<IllegalArgumentException> {
                vehicleStateContract.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeEngine should contain relevant predicate fields" {
            val vehicleStateEngine = TestUtils.getTestVehicleStateDataTypeEngineModel()
            vehicleStateEngine.predicateFields["power"] shouldNotBe null
            vehicleStateEngine.predicateFields["nonExistent"] shouldBe null
            vehicleStateEngine.retrieveFieldValue("power") shouldBe vehicleStateEngine.power
            vehicleStateEngine.retrieveFieldValue("capacity") shouldBe vehicleStateEngine.capacity
            vehicleStateEngine.retrieveFieldValue("chargingStatus") shouldBe vehicleStateEngine.chargingStatus
            vehicleStateEngine.retrieveFieldValue("transmissionType") shouldBe vehicleStateEngine.transmissionType
            shouldThrow<IllegalArgumentException> {
                vehicleStateEngine.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeFuel should contain relevant predicate fields" {
            val vehicleStateFuel = TestUtils.getTestVehicleStateDataTypeFuelModel()
            vehicleStateFuel.predicateFields["liters"] shouldNotBe null
            vehicleStateFuel.predicateFields["nonExistent"] shouldBe null
            vehicleStateFuel.retrieveFieldValue("levelLiters") shouldBe vehicleStateFuel.levelLiters
            vehicleStateFuel.retrieveFieldValue("levelPercentage") shouldBe vehicleStateFuel.levelPercentage
            vehicleStateFuel.retrieveFieldValue("remainingRange") shouldBe vehicleStateFuel.remainingRange
            vehicleStateFuel.retrieveFieldValue("tankCapacity") shouldBe vehicleStateFuel.tankCapacity
            shouldThrow<IllegalArgumentException> {
                vehicleStateFuel.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeMileage should contain relevant predicate fields" {
            val vehicleStateMileage = TestUtils.getTestVehicleStateDataTypeMileageModel()
            vehicleStateMileage.predicateFields["current"] shouldNotBe null
            vehicleStateMileage.predicateFields["nonExistent"] shouldBe null
            vehicleStateMileage.retrieveFieldValue("current") shouldBe vehicleStateMileage.current
            vehicleStateMileage.retrieveFieldValue("remaining") shouldBe vehicleStateMileage.remaining
            vehicleStateMileage.retrieveFieldValue("averagePerWeek") shouldBe vehicleStateMileage.averagePerWeek
            vehicleStateMileage.retrieveFieldValue("expectedExceedance") shouldBe vehicleStateMileage.expectedExceedance
            vehicleStateMileage.retrieveFieldValue("forecastEndContract") shouldBe vehicleStateMileage.forecastEndContract
            vehicleStateMileage.retrieveFieldValue("reachedPercentage") shouldBe vehicleStateMileage.reachedPercentage
            vehicleStateMileage.retrieveFieldValue("status") shouldBe vehicleStateMileage.status
            shouldThrow<IllegalArgumentException> {
                vehicleStateMileage.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeService should contain relevant predicate fields" {
            val vehicleStateDataTypeService = TestUtils.getTestVehicleStateDataTypeServiceModel()
            vehicleStateDataTypeService.predicateFields["dueDate"] shouldNotBe null
            vehicleStateDataTypeService.predicateFields["nonExistent"] shouldBe null
            vehicleStateDataTypeService.retrieveFieldValue("dueDate") shouldBe vehicleStateDataTypeService.dueDate
            vehicleStateDataTypeService.retrieveFieldValue("status") shouldBe vehicleStateDataTypeService.status
            vehicleStateDataTypeService.retrieveFieldValue("remainingDays") shouldBe vehicleStateDataTypeService.remainingDays
            vehicleStateDataTypeService.retrieveFieldValue("remainingMileage") shouldBe vehicleStateDataTypeService.remainingMileage
            shouldThrow<IllegalArgumentException> {
                vehicleStateDataTypeService.retrieveFieldValue("nonExistent")
            }
        }

        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestVehicleStateDataTypeModel,
                    { it.dataTypeId = 1 }
            )
        }
    }
}
