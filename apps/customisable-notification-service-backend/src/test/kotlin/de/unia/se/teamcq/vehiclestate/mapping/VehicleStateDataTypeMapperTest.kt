package de.unia.se.teamcq.vehiclestate.mapping

import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeBatteryEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeBatteryModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeContractEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeContractModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEngineEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEngineModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeFuelEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeFuelModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeMileageEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeMileageModel
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeServiceEntity
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeServiceModel
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class VehicleStateDataTypeMapperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var vehicleStateDataTypeBatteryMapper: IVehicleStateDataTypeBatteryMapper

    @MockK(relaxed = true)
    lateinit var vehicleStateDataTypeContractMapper: IVehicleStateDataTypeContractMapper

    @MockK(relaxed = true)
    lateinit var vehicleStateDataTypeEngineMapper: IVehicleStateDataTypeEngineMapper

    @MockK(relaxed = true)
    lateinit var vehicleStateDataTypeFuelMapper: IVehicleStateDataTypeFuelMapper

    @MockK(relaxed = true)
    lateinit var vehicleStateDataTypeMileageMapper: IVehicleStateDataTypeMileageMapper

    @MockK(relaxed = true)
    lateinit var vehicleStateDataTypeServiceMapper: IVehicleStateDataTypeServiceMapper

    @InjectMockKs
    lateinit var vehicleStateDataTypeMapper: VehicleStateDataTypeMapper

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            vehicleStateDataTypeMapper.modelToEntity(getTestVehicleStateDataTypeBatteryModel())
            verify(exactly = 1) {
                vehicleStateDataTypeBatteryMapper.modelToEntity(any())
            }

            vehicleStateDataTypeMapper.modelToEntity(getTestVehicleStateDataTypeContractModel())
            verify(exactly = 1) {
                vehicleStateDataTypeContractMapper.modelToEntity(any())
            }

            vehicleStateDataTypeMapper.modelToEntity(getTestVehicleStateDataTypeEngineModel())
            verify(exactly = 1) {
                vehicleStateDataTypeEngineMapper.modelToEntity(any())
            }

            vehicleStateDataTypeMapper.modelToEntity(getTestVehicleStateDataTypeFuelModel())
            verify(exactly = 1) {
                vehicleStateDataTypeFuelMapper.modelToEntity(any())
            }

            vehicleStateDataTypeMapper.modelToEntity(getTestVehicleStateDataTypeMileageModel())
            verify(exactly = 1) {
                vehicleStateDataTypeMileageMapper.modelToEntity(any())
            }

            vehicleStateDataTypeMapper.modelToEntity(getTestVehicleStateDataTypeServiceModel())
            verify(exactly = 1) {
                vehicleStateDataTypeServiceMapper.modelToEntity(any())
            }
        }

        "Convert entity to model" {

            vehicleStateDataTypeMapper.entityToModel(getTestVehicleStateDataTypeBatteryEntity())
            verify(exactly = 1) {
                vehicleStateDataTypeBatteryMapper.entityToModel(any())
            }

            vehicleStateDataTypeMapper.entityToModel(getTestVehicleStateDataTypeContractEntity())
            verify(exactly = 1) {
                vehicleStateDataTypeContractMapper.entityToModel(any())
            }

            vehicleStateDataTypeMapper.entityToModel(getTestVehicleStateDataTypeEngineEntity())
            verify(exactly = 1) {
                vehicleStateDataTypeEngineMapper.entityToModel(any())
            }

            vehicleStateDataTypeMapper.entityToModel(getTestVehicleStateDataTypeFuelEntity())
            verify(exactly = 1) {
                vehicleStateDataTypeFuelMapper.entityToModel(any())
            }

            vehicleStateDataTypeMapper.entityToModel(getTestVehicleStateDataTypeMileageEntity())
            verify(exactly = 1) {
                vehicleStateDataTypeMileageMapper.entityToModel(any())
            }

            vehicleStateDataTypeMapper.entityToModel(getTestVehicleStateDataTypeServiceEntity())
            verify(exactly = 1) {
                vehicleStateDataTypeServiceMapper.entityToModel(any())
            }
        }
    }
}
