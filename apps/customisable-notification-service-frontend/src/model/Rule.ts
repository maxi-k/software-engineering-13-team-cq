import { DeepPartial } from 'ts-essentials'
import { Fleet } from './CarPark'
import { VehicleDataField } from './Vehicle'

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
  Immediate,
  Counting,
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
  ruleId: number
  name: string
  description: string
  aggregator: Aggregator
  condition: RuleCondition
}

export interface NotificationRuleDetail
  extends NotificationRuleOverview {
  owner: NotificationRuleOwner
  recipients: NotificationRecipient[]
  ownerAsAdditionalRecipient: boolean
  applyToAllFleets: boolean
  fleets: Fleet[]
}

export type NotificationRuleInProgress
  = Partial<NotificationRuleDetail> & {
    condition: DeepPartial<RuleCondition>
  }
