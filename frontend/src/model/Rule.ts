export enum VehicleDataType {
  Battery = 'Battery',
  Engine = 'Engine',
  Contract = 'Contract',
  Mileage = 'Mileage',
  Fuel = 'Fuel',
  Service = 'Service'
}

export enum ComparisonType {
  Above = 'Above',
  Below = 'Below',
  Equal = 'Equal to',
  NonEqual = 'Unequal to'
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
