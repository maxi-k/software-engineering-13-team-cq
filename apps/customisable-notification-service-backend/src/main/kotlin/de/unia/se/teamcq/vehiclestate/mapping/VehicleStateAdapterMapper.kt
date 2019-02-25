package de.unia.se.teamcq.vehiclestate.mapping

import de.bmw.vss.model.Vehicle
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
import org.springframework.stereotype.Component
import org.threeten.bp.DateTimeUtils
import java.lang.NullPointerException

@Component
class VehicleStateAdapterMapper : IVehicleStateAdapterMapper {

    @Throws(NullPointerException::class)
    override fun dtoToModel(vehicle: Vehicle): VehicleState {

        val vehicleStateDataTypes = getVehicleStateDataTypes(vehicle)

        val fleetReference = FleetReference(vehicle.fleet!!.toString(), vehicle.carPark!!.toString())
        val vehicleReference = getVehicleReference(vehicle, fleetReference)

        return VehicleState(
                vehicleReference = vehicleReference,
                vehicleStateDataTypes = vehicleStateDataTypes
        )
    }

    private fun getVehicleReference(vehicle: Vehicle, fleetReference: FleetReference): VehicleReference {
        return VehicleReference(
                vehicle.vin,
                fleetReference,
                vehicle.brand,
                vehicle.model,
                vehicle.series,
                vehicle.note
        )
    }

    private fun getVehicleStateDataTypes(vehicle: Vehicle): Set<VehicleStateDataType> {

        val battery = getVehicleStateDataTypeBattery(vehicle)
        val engine = getVehicleStateDataTypeEngine(vehicle)
        val fuel = getVehicleStateDataTypeFuel(vehicle)
        val service = getVehicleStateDataTypeService(vehicle)
        val contract = getVehicleStateDataTypeContract(vehicle)
        val mileage = getVehicleStateDataTypeMileage(vehicle)

        return setOf(
                battery,
                engine,
                fuel,
                service,
                contract,
                mileage
        )
    }

    private fun getVehicleStateDataTypeMileage(vehicle: Vehicle): VehicleStateDataTypeMileage {
        return VehicleStateDataTypeMileage(
                vehicle.mileage?.current,
                vehicle.mileage?.remaining,
                vehicle.mileage?.reachedPercentage,
                vehicle.mileage?.averagePerWeek,
                vehicle.mileage?.expectedExceedance,
                vehicle.mileage?.forecastEndContract,
                vehicle.mileage?.status?.toString()
        )
    }

    private fun getVehicleStateDataTypeContract(vehicle: Vehicle): VehicleStateDataTypeContract {
        return VehicleStateDataTypeContract(
                vehicle.contract?.endMileage,
                vehicle.contract?.endDate?.let { date -> DateTimeUtils.toSqlDate(date) },
                vehicle.contract?.reachedRuntimePercentage,
                vehicle.contract?.remainingDays,
                vehicle.contract?.startDate?.let { date -> DateTimeUtils.toSqlDate(date) },
                vehicle.contract?.startMileage
        )
    }

    private fun getVehicleStateDataTypeService(vehicle: Vehicle): VehicleStateDataTypeService {

        return VehicleStateDataTypeService(
                vehicle.serviceStatus?.status?.toString(),
                vehicle.serviceStatus?.dueDate,
                vehicle.serviceStatus?.remainingDays,
                vehicle.serviceStatus?.remainingMileage
        )
    }

    private fun getVehicleStateDataTypeFuel(vehicle: Vehicle): VehicleStateDataTypeFuel {
        return VehicleStateDataTypeFuel(
                vehicle.fuel?.levelPercentage,
                vehicle.fuel?.levelLiters,
                vehicle.fuel?.tankCapacity,
                vehicle.fuel?.remainingRange
        )
    }

    private fun getVehicleStateDataTypeEngine(vehicle: Vehicle): VehicleStateDataTypeEngine {
        return VehicleStateDataTypeEngine(
                vehicle.engine?.power,
                vehicle.engine?.capacity,
                vehicle.engine?.fuelType,
                vehicle.engine?.transmissionType
        )
    }

    private fun getVehicleStateDataTypeBattery(vehicle: Vehicle): VehicleStateDataTypeBattery {
        return VehicleStateDataTypeBattery(
                vehicle.battery?.levelPercentage?.toDouble(),
                vehicle.battery?.voltage?.toDouble(),
                vehicle.battery?.chargingStatus,
                vehicle.battery?.remainingChargingHours,
                vehicle.battery?.remainingRange
        )
    }
}
