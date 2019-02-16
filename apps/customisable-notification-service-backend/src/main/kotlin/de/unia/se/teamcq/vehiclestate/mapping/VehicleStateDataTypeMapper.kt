package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorScheduledDto
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorCountingEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorImmediateEntity
import de.unia.se.teamcq.notificationmanagement.entity.AggregatorScheduledEntity
import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.AggregatorCounting
import de.unia.se.teamcq.notificationmanagement.model.AggregatorImmediate
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
import de.unia.se.teamcq.vehiclestate.entity.*
import de.unia.se.teamcq.vehiclestate.model.*
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
