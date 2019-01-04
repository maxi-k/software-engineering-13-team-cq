export enum VehicleDataType {
  Battery,
  Engine,
  Contract,
  Mileage,
  Fuel,
  Service
}

export enum NotificationRecipientType {
  Email,
  PhoneNumber,
  User
}

export interface NotificationRecipient {
  type: NotificationRecipientType,
  value: string
}

export interface NotificationRuleOverview {
  id: number,
  name: string,
  description: string,
  dataSources: VehicleDataType[],
  aggregatorDescription: string
}

export interface NotificationRuleDetail
extends NotificationRuleOverview {
  recipients: NotificationRecipient[]
}
