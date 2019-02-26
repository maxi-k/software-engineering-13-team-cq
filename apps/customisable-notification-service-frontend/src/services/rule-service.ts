import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { constantly } from '@/utilities/function-util'
import { capitalizeString } from '@/utilities/string-util'
import {
  NotificationRuleOverview as OverviewRule,
  NotificationRuleDetail as DetailRule,
  NotificationRecipient as Recipient,
  NotificationRuleOwner as RuleOwner,
  NotificationRecipient as RuleRecipient,
  NotificationRecipientType as RecipientType,
  RuleCondition,
  LogicalConnective,
  VehicleDataType,
  AggregatorStrategy,
  Aggregator,
  Fleet,
  RuleConditionPredicate,
  VehicleDataField
} from '@/model'
import { transformObject, transformObjects, pickMap, FieldValidator } from '@/utilities/collection-util';
import { isValidCronExpression } from '@/utilities/cron-util';

const ruleOverviewUrl = '/notification-rule-management/notification-rule'
export const fetchRuleOverview = (accessToken: string) => (
  doMock
    ? mockRequest(ruleOverviewUrl, mockedRuleOverview)
    : authApiRequest(ruleOverviewUrl, accessToken)
)

const ruleDetailUrl = (ruleId: number | string) => `/notification-rule-management/notification-rule/${ruleId}`
export const fetchRuleDetail = (accessToken: string, ruleId: number) => (
  doMock
    ? (
      ruleId >= mockedRuleOverview.length
        ? mockRequest(ruleDetailUrl(ruleId), { status: 404 })
        : mockRequest(ruleDetailUrl(ruleId), mockedRuleDetail(ruleId))
    )
    : authApiRequest(ruleDetailUrl(ruleId), accessToken)
)

export const deleteRule = (accessToken: string, ruleId: number) => (
  doMock
    ? (
      // We want a console print here, as there will be no information in
      // the network tab, and this is development only.
      // tslint:disable-next-line:no-console
      console.info("Mocking deletion of remote NotificationRule with ID " + ruleId)
    )
    : authApiRequest(ruleDetailUrl(ruleId), accessToken, {
      method: 'DELETE'
    })
)

const ruleCreationUrl = '/notification-rule-management/notification-rule'
export const createNewRule = (accessToken: string, rule: APIRule) => (
  doMock
    ? mockRequest(ruleDetailUrl(0), mockedRuleDetail(0))
    : authApiRequest(ruleCreationUrl, accessToken, {
      method: 'POST',
      body: JSON.stringify(rule)
    })
)

const ruleEditingUrl = (ruleId: number | string) => `/notification-rule-management/notification-rule/${ruleId}`
export const editRule = (accessToken: string, rule: APIRule) => (
  doMock
    ? mockRequest(ruleDetailUrl(0), mockedRuleDetail(0))
    : authApiRequest(ruleEditingUrl(rule.ruleId), accessToken, {
      method: 'PUT',
      body: JSON.stringify(rule)
    })
)

type APIRecipient = { '@type': 'RecipientMailDto', mailAddress: string }
  | { '@type': 'RecipientSmsDto', phoneNumber: string }

export interface APIRule {
  ruleId: number
  name: string
  description: string
  owner: {
    name: string
    mailAddress: string
    cellPhoneNumber: string
    userSettings: {
      userNotificationType: 'EMAIL' | 'SMS'
    },
  }
  affectedFleets: object[]
  condition: APICondition
  recipients: APIRecipient[]
  affectingAllApplicableFleets: boolean
  ownerAsAdditionalRecipient: boolean
  aggregator: object
}

interface APICondition {
  '@type': string,
  conditionId: number,
  logicalConnective: 'NONE' | 'ANY' | 'ALL'
  subConditions: APIPredicate[]
}

interface APIPredicate {
  '@type': string,
  conditionId: number,
  providerName: string,
  fieldName: string,
  comparisonType: string,
  comparisonValue: string
}

const convertAggregatorType = (aggregatorStrategy: AggregatorStrategy): string => (
  `Aggregator${AggregatorStrategy[aggregatorStrategy]}Dto`
)

const convertAggregatorValueKey = (aggregator: Aggregator): object => {
  switch (aggregator.strategy) {
    case AggregatorStrategy.Counting:
      return { notificationCountThreshold: aggregator.value }
    case AggregatorStrategy.Scheduled:
      // Append second and year to cron expression to
      // match the spring format
      return { notificationCronTrigger: '0 ' + aggregator.value }
    case AggregatorStrategy.Immediate:
    default:
      return {}
  }
}

const convertAPIAggregator = (apiAggregator: { [key: string]: any }): Aggregator => {
  switch (apiAggregator['@type']) {
    case 'AggregatorScheduledDto':
      return {
        strategy: AggregatorStrategy.Scheduled,
        // Delete second and year to cron expression to
        // convert from the spring format to the standard format
        value: apiAggregator.notificationCronTrigger.substring(2, apiAggregator.notificationCronTrigger.length)
      }
    case 'AggregatorCountingDto':
      return {
        strategy: AggregatorStrategy.Counting,
        value: apiAggregator.notificationCountThreshold
      }
    case 'AggregatorImmediateDto':
    default:
      return { strategy: AggregatorStrategy.Immediate }
  }
}

export const transformFromAPIRuleRecipients = (oldRecipients: APIRecipient[]): RuleRecipient[] => (
  oldRecipients.map((recipient) => {
    switch (recipient['@type']) {
      case 'RecipientMailDto':
        return {
          type: RecipientType.Email,
          value: recipient.mailAddress
        }
      case 'RecipientSmsDto':
        return {
          type: RecipientType.PhoneNumber,
          value: recipient.phoneNumber
        }
    }
  })
)

export const convertFromAPIRule = (rule: APIRule): DetailRule => transformObject(rule, {
  affectedFleets: 'fleets',
  affectingAllApplicableFleets: 'applyToAllFleets',
  aggregator: (apiAggregator: any) => (
    ['aggregator', convertAPIAggregator(apiAggregator as object)] as [string, Aggregator]
  ),
  owner: 'owner',
  recipients: (recipients: any) => (['recipients', transformFromAPIRuleRecipients(recipients)] as [string, RuleRecipient[]]),
  ownerAsAdditionalRecipient: 'ownerAsAdditionalRecipient',
  condition: (condition: any) => (['condition', transformObject(condition, {
    '@type': (typeValue: any) => ['@type', undefined] as [string, undefined],
    logicalConnective: ((connective: any): [string, LogicalConnective] => (
      ['logicalConnective', (connective as string).toLowerCase() as LogicalConnective]
    )),
    subConditions: (subConditions: any): [string, RuleCondition['predicates']] => [
      'predicates',
      (subConditions as APIPredicate[]).reduce((predicateMap, predicate: APIPredicate) => (
        {
          ...predicateMap,
          [predicate.conditionId]: {
            comparisonType: predicate.comparisonType,
            comparisonConstant: predicate.comparisonValue,
            appliedField: {
              vehicleDataType: predicate.providerName,
              predicateField: {
                fieldName: predicate.fieldName,
              }
            }
          } as RuleConditionPredicate<any>
        }), {}) as { [key: string]: RuleConditionPredicate<any> }]
  })
  ] as [string, RuleCondition])
}) as DetailRule

export const transformToAPIRuleRecipients = (oldRecipients: RuleRecipient[]): APIRecipient[] => (
  oldRecipients.map((recipient) => {
    switch (recipient.type) {
      case RecipientType.Email:
        return {
          '@type': 'RecipientMailDto' as 'RecipientMailDto',
          mailAddress: recipient.value
        }
      case RecipientType.PhoneNumber:
      default:
        return {
          '@type': 'RecipientSmsDto' as 'RecipientSmsDto',
          phoneNumber: recipient.value
        }
    }
  })
)

export const convertToAPIRule = (rule: DetailRule): APIRule => transformObject(rule, {
  fleets: (oldFleets: any) => ([
    'affectedFleets',
    pickMap(oldFleets as Fleet[], 'fleetId', 'carParkId')
  ] as [string, object[]]),
  applyToAllFleets: 'affectingAllApplicableFleets',
  recipients: (oldRecipients: any) => ([
    'recipients',
    transformToAPIRuleRecipients(oldRecipients)
  ] as [string, object[]]),
  condition: (oldCondition: any) => (['condition', transformObject(oldCondition, {
    logicalConnective: (connective: any) => (['logicalConnective', connective.toUpperCase()] as [string, string]),
    predicates: (predicates: any) => (['subConditions', transformObjects(Object.values(predicates), {
      appliedField: [
        (value: any) => {
          return [
            'providerName',
            capitalizeString((value as VehicleDataField<any>).vehicleDataType)
          ]
        },
        (value: any) => [
          'fieldName',
          (value as VehicleDataField<any>).predicateField.fieldName
        ],
        (value: any, outerObject: RuleConditionPredicate<any>) => [
          'comparisonType',
          outerObject.comparisonType
        ],
        (value: any, outerObject: RuleConditionPredicate<any>) => [
          'comparisonValue',
          outerObject.comparisonConstant
        ],
        (value: any) => ['appliedField', null]
      ]
    }, {
        '@type': 'RuleConditionPredicateDto',
      })] as [string, object[]])
  }, {
      '@type': 'RuleConditionCompositeDto'
    })] as [string, object]),
  aggregator: (aggregator: any) => (['aggregator', {
    '@type': convertAggregatorType(aggregator.strategy),
    ...convertAggregatorValueKey(aggregator as Aggregator)
  }] as [string, object])
}) as APIRule

export const ruleOwnerAsRecipient = (ruleOwner: RuleOwner): Recipient => (
  ruleOwner.userSettings.userNotificationType === 'EMAIL'
    ? {
      type: RecipientType.Email,
      value: ruleOwner.mailAddress || 'unknown'
    } : {
      type: RecipientType.PhoneNumber,
      value: ruleOwner.cellPhoneNumber || 'unknown'
    }
)

export const ruleFieldValidator = new FieldValidator<DetailRule>({
  ruleId: constantly(true),
  name: ((rule) => FieldValidator.validateNonEmptyString(rule.name)),
  description: ((rule) => FieldValidator.validateNonEmptyString(rule.description)),
  owner: ((rule) => FieldValidator.validateExists(rule.owner)),
  recipients: ((rule) => FieldValidator.validateEvery(rule.recipients, (recipient: RuleRecipient) => (
    (recipient.type === RecipientType.Email && FieldValidator.validateEmailAddress(recipient.value)) ||
    (recipient.type !== RecipientType.Email)
  )) && (FieldValidator.validateEquals(rule.ownerAsAdditionalRecipient, true) ||
    (Array.isArray(rule.recipients) && rule.recipients.length > 0))
  ),
  ownerAsAdditionalRecipient: ((rule) => FieldValidator.validateExists(rule.ownerAsAdditionalRecipient)),
  applyToAllFleets: ((rule) => FieldValidator.validateExists(rule.applyToAllFleets)),
  fleets: ((rule) => FieldValidator.validateIsArray(rule.fleets) &&
    (FieldValidator.validateEquals(rule.applyToAllFleets, true) ||
      (Array.isArray(rule.fleets) && rule.fleets.length > 0))
  ),
  condition: (({ condition }) =>
    FieldValidator.validateExists(condition) && typeof condition !== 'undefined' &&
    Object.values(condition.predicates).length > 0 &&
    FieldValidator.validateEvery(Object.values(condition.predicates), (predicate: RuleConditionPredicate<any>) => (
      FieldValidator.validateExists(predicate.comparisonConstant) &&
      FieldValidator.validateExists(predicate.appliedField) &&
      FieldValidator.validateExists(predicate.comparisonType)
    ))),
  aggregator: (({ aggregator }) => typeof aggregator !== 'undefined' && aggregator !== null && (
    (aggregator.strategy === AggregatorStrategy.Immediate) ||
    (aggregator.strategy === AggregatorStrategy.Counting &&
      (typeof aggregator.value === 'number' || !isNaN(parseInt(aggregator.value || '', 10)))) ||
    (aggregator.strategy === AggregatorStrategy.Scheduled &&
      typeof aggregator.value !== 'undefined' &&
      isValidCronExpression(aggregator.value.toString()))
  ))
},
  (rule: Partial<DetailRule>, fieldName: keyof DetailRule) => `cns.rule.validation.field.${fieldName}.message`
)

export const getRuleDataSources = (rule: Partial<OverviewRule>): VehicleDataType[] => {
  if (typeof rule.condition === 'undefined' || typeof rule.condition.predicates === 'undefined'
    || rule.condition === null || rule.condition.predicates === null) {
    return []
  }
  return Array.from(Object.values(rule.condition.predicates)
    .reduce((dataTypes: Set<VehicleDataType>,
      predicate: RuleConditionPredicate<any>) => (
        dataTypes.add(predicate.appliedField.vehicleDataType)
      ), new Set())
  )
}

/* BEGIN: Mocks */

const mockedRule: OverviewRule = {
  ruleId: 0,
  name: 'Status Reports',
  description: 'Rule Description for an examplary Notification Rule',
  condition: {
    logicalConnective: LogicalConnective.All,
    predicates: {}
  },
  aggregator: {
    strategy: AggregatorStrategy.Counting,
    value: 20
  }
}

const mockedRule2: OverviewRule = {
  ...mockedRule,
  ruleId: 1,
  name: 'Engine and Fuel Alerts'
}

export const mockedRuleOverview: OverviewRule[] = [mockedRule, mockedRule2]

export const mockedRuleDetail = (ruleId: number): DetailRule => ({
  ruleId,
  ...mockedRuleOverview[ruleId % mockedRuleOverview.length],
  applyToAllFleets: true,
  fleets: [],
  owner: {
    name: 'Test Owner',
    mailAddress: 'test@example.com',
    userSettings: {
      userNotificationType: 'EMAIL'
    }
  },
  ownerAsAdditionalRecipient: true,
  recipients: [{ type: RecipientType.PhoneNumber, value: '+49 1234567890' }]
})
