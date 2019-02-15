package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.TestUtils
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.lang.IllegalArgumentException

@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleStateDataTypeTest: StringSpec() {

    init {
        "VehicleStateDataTypeBattery should contain relevant predicate fields" {
            val vehicleStateBattery = TestUtils.getTestVehicleStateDataTypeBatteryModel()
            vehicleStateBattery.predicateFields["charge"] shouldNotBe null
            vehicleStateBattery.predicateFields["nonExistent"] shouldBe null
            vehicleStateBattery.retrieveFieldValue("charge") shouldBe vehicleStateBattery.charge
            vehicleStateBattery.retrieveFieldValue("status") shouldBe vehicleStateBattery.status
            vehicleStateBattery.retrieveFieldValue("voltage") shouldBe vehicleStateBattery.voltage
            shouldThrow<IllegalArgumentException> {
                vehicleStateBattery.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeContract should contain relevant predicate fields" {
            val vehicleStateContract = TestUtils.getTestVehicleStateDataTypeContractModel()
            vehicleStateContract.predicateFields["duePerWeek"] shouldNotBe null
            vehicleStateContract.predicateFields["nonExistent"] shouldBe null
            vehicleStateContract.retrieveFieldValue("duePerWeek") shouldBe vehicleStateContract.duePerWeek
            vehicleStateContract.retrieveFieldValue("calendarWeek") shouldBe vehicleStateContract.calendarWeek
            vehicleStateContract.retrieveFieldValue("vins") shouldBe vehicleStateContract.vins
            shouldThrow<IllegalArgumentException> {
                vehicleStateContract.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeEngine should contain relevant predicate fields" {
            val vehicleStateEngine = TestUtils.getTestVehicleStateDataTypeEngineModel()
            vehicleStateEngine.predicateFields["power"] shouldNotBe null
            vehicleStateEngine.predicateFields["nonExistent"] shouldBe null
            vehicleStateEngine.retrieveFieldValue("power") shouldBe vehicleStateEngine.power
            vehicleStateEngine.retrieveFieldValue("fuelType") shouldBe vehicleStateEngine.fuelType
            vehicleStateEngine.retrieveFieldValue("capacity") shouldBe vehicleStateEngine.capacity
            shouldThrow<IllegalArgumentException> {
                vehicleStateEngine.retrieveFieldValue("nonExistent")
            }
        }

        "VehicleStateDataTypeFuel should contain relevant predicate fields" {
            val vehicleStateFuel = TestUtils.getTestVehicleStateDataTypeFuelModel()
            vehicleStateFuel.predicateFields["liters"] shouldNotBe null
            vehicleStateFuel.predicateFields["nonExistent"] shouldBe null
            vehicleStateFuel.retrieveFieldValue("liters") shouldBe vehicleStateFuel.liters
            vehicleStateFuel.retrieveFieldValue("level") shouldBe vehicleStateFuel.level
            vehicleStateFuel.retrieveFieldValue("range") shouldBe vehicleStateFuel.range
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
            vehicleStateMileage.retrieveFieldValue("reached") shouldBe vehicleStateMileage.reached
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
            vehicleStateDataTypeService.retrieveFieldValue("brakeFluid") shouldBe vehicleStateDataTypeService.brakeFluid
            shouldThrow<IllegalArgumentException> {
                vehicleStateDataTypeService.retrieveFieldValue("nonExistent")
            }
        }
    }
}