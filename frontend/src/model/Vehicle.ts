export enum VehicleDataType {
  Battery = 'battery',
  Engine = 'engine',
  Contract = 'contract',
  Mileage = 'mileage',
  Fuel = 'fuel',
  Service = 'service'
}

export enum VehicleDataFieldType {
  String,
  Number,
  Percentage,
  Date
}

export interface VehicleDataField {
  type: VehicleDataFieldType,
  fieldType: any,
  fieldName: string
}
export interface VehicleDataFieldString
  extends VehicleDataField {
  type: VehicleDataFieldType.String,
  fieldType: string
}
export interface VehicleDataFieldPercentage
  extends VehicleDataField {
  type: VehicleDataFieldType.Percentage,
  fieldType: number
}
export interface VehicleDataFieldNumber
  extends VehicleDataField {
  type: VehicleDataFieldType.Number,
  fieldType: number
}
export interface VehicleDataFieldDate
  extends VehicleDataField {
  type: VehicleDataFieldType.Date,
  fieldType: Date
}
