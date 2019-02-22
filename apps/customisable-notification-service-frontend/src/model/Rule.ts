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

export enum AggregatorStrategy {
  Counting,
  Immediate,
  Scheduled
}

export interface Aggregator {
  strategy: AggregatorStrategy
  value?: string | number
}

export interface RuleCondition {
  logicalConnective: LogicalConnective,
  // Only model one layer of the composite for now...
  predicates: { [key: string]: RuleConditionPredicate<any> }
}

export interface RuleConditionPredicate<FieldType> {
  appliedField: VehicleDataField<FieldType>,
  comparisonConstant: FieldType,
  comparisonType: string
}

export interface NotificationRecipient {
  type: NotificationRecipientType,
  value: string
}

export interface NotificationRuleOwner {
  name: string
  mailAddress?: string
  cellPhoneNumber?: string
  userSettings: {
    settingsId?: number,
    userNotificationType: 'EMAIL' | 'SMS'
  }
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
  owner: NotificationRuleOwner
  recipients: NotificationRecipient[]
  ownerAsAdditionalRecipient: boolean
  applyToAllFleets: boolean
  fleets: Fleet[]
  condition: RuleCondition
  aggregator: Aggregator
}

export type NotificationRuleInProgress
  = Partial<NotificationRuleDetail> & {
    condition: DeepPartial<RuleCondition>
  }
