import { Fleet } from './CarPark'
import { VehicleDataField, VehicleDataType } from './Vehicle'

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

export enum LogicalConnective {
  All = 'all',
  Any = 'any',
  None = 'none'
}

export interface ConditionComposite {
  logicalConnective: LogicalConnective,
  // Only model one layer of the composite for now...
  subConditions: Set<Condition<any>>
}

export interface Condition<T extends VehicleDataField> {
  appliedField: T,
  comparisonConstant: T['fieldType']
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
  applyToAllFleets: boolean,
  fleets: Fleet[],
  recipients: NotificationRecipient[],
  condition: ConditionComposite
}

export interface NotificationRuleInProgress
  extends Partial<NotificationRuleDetail> {
}
