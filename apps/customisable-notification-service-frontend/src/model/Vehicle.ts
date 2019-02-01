import { PredicateField } from './PredicateField'

export enum VehicleDataType {
  Battery = 'battery',
  Engine = 'engine',
  Contract = 'contract',
  Mileage = 'mileage',
  Fuel = 'fuel',
  Service = 'service'
}

export interface VehicleDataField<DataType> {
  vehicleDataType: VehicleDataType,
  predicateField: PredicateField<DataType>
}

// As returned by the API
export interface VehiclePredicateFields {
  providerName: VehicleDataType,
  predicateFields: Array<PredicateField<any>>
}
