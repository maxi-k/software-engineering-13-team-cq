package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeContractEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEngineEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeFuelEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeMileageEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeServiceEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeBattery
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeContract
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeEngine
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeFuel
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeMileage
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeMapper : IVehicleStateDataTypeMapper {

    @Autowired
    lateinit var vehicleStateDataTypeBatteryMapper: IVehicleStateDataTypeBatteryMapper

    @Autowired
    lateinit var vehicleStateDataTypeContractMapper: IVehicleStateDataTypeContractMapper

    @Autowired
    lateinit var vehicleStateDataTypeEngineMapper: IVehicleStateDataTypeEngineMapper

    @Autowired
    lateinit var vehicleStateDataTypeFuelMapper: IVehicleStateDataTypeFuelMapper

    @Autowired
    lateinit var vehicleStateDataTypeMileageMapper: IVehicleStateDataTypeMileageMapper

    @Autowired
    lateinit var vehicleStateDataTypeServiceMapper: IVehicleStateDataTypeServiceMapper

    override fun modelToEntity(vehicleStateDataType: VehicleStateDataType): VehicleStateDataTypeEntity {
        return when (vehicleStateDataType) {
            is VehicleStateDataTypeBattery -> vehicleStateDataTypeBatteryMapper.modelToEntity(vehicleStateDataType)
            is VehicleStateDataTypeContract -> vehicleStateDataTypeContractMapper.modelToEntity(vehicleStateDataType)
            is VehicleStateDataTypeEngine -> vehicleStateDataTypeEngineMapper.modelToEntity(vehicleStateDataType)
            is VehicleStateDataTypeFuel -> vehicleStateDataTypeFuelMapper.modelToEntity(vehicleStateDataType)
            is VehicleStateDataTypeMileage -> vehicleStateDataTypeMileageMapper.modelToEntity(vehicleStateDataType)
            is VehicleStateDataTypeService -> vehicleStateDataTypeServiceMapper.modelToEntity(vehicleStateDataType)
            else -> throw IllegalArgumentException("Unknown VehicleStateDataType: $vehicleStateDataType")
        }
    }

    override fun entityToModel(vehicleStateDataTypeEntity: VehicleStateDataTypeEntity): VehicleStateDataType {
        return when (vehicleStateDataTypeEntity) {
            is VehicleStateDataTypeBatteryEntity -> vehicleStateDataTypeBatteryMapper.entityToModel(vehicleStateDataTypeEntity)
            is VehicleStateDataTypeContractEntity -> vehicleStateDataTypeContractMapper.entityToModel(vehicleStateDataTypeEntity)
            is VehicleStateDataTypeEngineEntity -> vehicleStateDataTypeEngineMapper.entityToModel(vehicleStateDataTypeEntity)
            is VehicleStateDataTypeFuelEntity -> vehicleStateDataTypeFuelMapper.entityToModel(vehicleStateDataTypeEntity)
            is VehicleStateDataTypeMileageEntity -> vehicleStateDataTypeMileageMapper.entityToModel(vehicleStateDataTypeEntity)
            is VehicleStateDataTypeServiceEntity -> vehicleStateDataTypeServiceMapper.entityToModel(vehicleStateDataTypeEntity)
            else -> throw IllegalArgumentException("Unknown VehicleStateDataTypeEntity: $vehicleStateDataTypeEntity")
        }
    }
}
