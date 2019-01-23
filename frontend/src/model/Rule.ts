import { DeepPartial } from 'ts-essentials'
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

export interface RuleCondition {
  logicalConnective: LogicalConnective,
  // Only model one layer of the composite for now...
  predicates: { [key: string]: RuleConditionPredicate<any> }
}

export interface RuleConditionPredicate<FieldType> {
  vehicleDataType: VehicleDataType,
  appliedField: VehicleDataField<FieldType>,
  comparisonConstant: FieldType,
  comparisonField: ComparisonType
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
  condition: RuleCondition
}

export type NotificationRuleInProgress
  = Partial<NotificationRuleDetail> & {
    condition: DeepPartial<RuleCondition>
  }
