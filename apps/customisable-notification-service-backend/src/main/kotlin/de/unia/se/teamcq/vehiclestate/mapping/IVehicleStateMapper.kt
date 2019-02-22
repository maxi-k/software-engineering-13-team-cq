package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.vehiclestate.entity.VehicleStateDataTypeEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntity
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import de.unia.se.teamcq.vehiclestate.model.VehicleStateDataType
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", uses = [IVehicleReferenceMapper::class, VehicleStateMapperHelper::class])
interface IVehicleStateMapper {

    fun modelToEntity(vehicleState: VehicleState): VehicleStateEntity

    fun entityToModel(vehicleStateEntity: VehicleStateEntity): VehicleState
}

@Component
class VehicleStateMapperHelper {

    @Autowired
    lateinit var vehicleStateDataTypeMapper: IVehicleStateDataTypeMapper

    fun modelToEntity(set: Set<VehicleStateDataType>): Set<VehicleStateDataTypeEntity> {

        return set.map { vehicleStateDataType ->
            vehicleStateDataTypeMapper.modelToEntity(vehicleStateDataType)
        }.toSet()
    }

    fun entityToModel(set: Set<VehicleStateDataTypeEntity>): Set<VehicleStateDataType> {

        return set.map { vehicleStateDataType ->
            vehicleStateDataTypeMapper.entityToModel(vehicleStateDataType)
        }.toSet()
    }
}
