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
import javax.xml.bind.DatatypeConverter

@Component
class VehicleStateAdapterMapper : IVehicleStateAdapterMapper {

    override fun dtoToModel(vehicle: Vehicle): VehicleState {

        val vehicleStateDataTypes = getVehicleStateDataTypes(vehicle)

        val fleetReference = FleetReference(vehicle.fleet?.toString(), vehicle.carPark?.toString())
        val vehicleReference = VehicleReference(vehicle.vin, fleetReference)

        return VehicleState(
                vehicleReference = vehicleReference,
                vehicleStateDataTypes = vehicleStateDataTypes
        )
    }

    private fun getVehicleStateDataTypes(vehicle: Vehicle): Set<VehicleStateDataType> {

        val battery = getVehicleStateDataTypeBattery(vehicle)
        val engine = getVehicleStateDataTypeEngine(vehicle)
        val fuel = getVehicleStateDataTypeFuel(vehicle)
        val service = getVehicleStateDataTypeService(vehicle)
        val contract = getVehicleStateDataTypeContract()
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
                vehicle.mileage?.reachedPercentage
        )
    }

    private fun getVehicleStateDataTypeContract(): VehicleStateDataTypeContract {
        // FIXME: Make contract more similar to VSS-Version
        return VehicleStateDataTypeContract(
                10,
                setOf("1002A", "1008B"),
                9
        )
    }

    private fun getVehicleStateDataTypeService(vehicle: Vehicle): VehicleStateDataTypeService {

        val dueDate = vehicle.serviceStatus?.dueDate?.let { dueDate ->
            DatatypeConverter.parseDateTime(dueDate)
        }?.time

        return VehicleStateDataTypeService(
                dueDate,
                vehicle.serviceStatus?.brakeFluid?.status?.toString(),
                vehicle.serviceStatus?.status?.toString()
        )
    }

    private fun getVehicleStateDataTypeFuel(vehicle: Vehicle): VehicleStateDataTypeFuel {
        return VehicleStateDataTypeFuel(
                vehicle.fuel?.levelPercentage,
                vehicle.fuel?.levelLiters,
                vehicle.fuel?.remainingRange
        )
    }

    private fun getVehicleStateDataTypeEngine(vehicle: Vehicle): VehicleStateDataTypeEngine {
        return VehicleStateDataTypeEngine(
                vehicle.engine?.power,
                vehicle.engine?.capacity,
                vehicle.engine?.fuelType
        )
    }

    private fun getVehicleStateDataTypeBattery(vehicle: Vehicle): VehicleStateDataTypeBattery {
        return VehicleStateDataTypeBattery(
                vehicle.battery?.levelPercentage?.toDouble(),
                vehicle.battery?.voltage?.toDouble(),
                vehicle.battery?.chargingStatus
        )
    }
}
