export enum VehicleDataType {
  Battery = 'battery',
  Engine = 'engine',
  Contract = 'contract',
  Mileage = 'mileage',
  Fuel = 'fuel',
  Service = 'service'
}

export enum NotificationRecipientType {
  Email,
  PhoneNumber,
  User
}

export enum PredicateCounterValue {
  All = "All",
  Any = "Any",
  None = "None"
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
