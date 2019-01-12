export enum VehicleDataType {
  Battery = 'battery',
  Engine = 'engine',
  Contract = 'contract',
  Mileage = 'mileage',
  Fuel = 'fuel',
  Service = 'service'
}

export enum ComparisonType {
  Above = 'above',
  Below = 'below',
  EqualTo = 'equalTo',
  UnequalTo = 'unequalTo'
}

export enum NotificationRecipientType {
  Email = 'email',
  PhoneNumber = 'phoneNumber',
  User = 'user'
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
  ruleId: number,
  name: string,
  description: string,
  dataSources: VehicleDataType[],
  aggregatorDescription: string
}

export interface NotificationRuleDetail
extends NotificationRuleOverview {
  recipients: NotificationRecipient[]
}
