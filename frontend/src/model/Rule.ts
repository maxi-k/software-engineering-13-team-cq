export enum VehicleDataType {
  Battery,
  Engine,
  Contract,
  Mileage,
  Fuel,
  Service
}

export interface NotificationRule_Overview {
  id: number,
  name: string,
  description: string,
  dataSources: VehicleDataType[],
  aggregatorDescription: string
}

export interface NotificationRule_Detail
extends NotificationRule_Overview {

}
