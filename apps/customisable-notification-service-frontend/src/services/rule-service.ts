import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { capitalizeString } from '@/utilities/string-util'
import {
  NotificationRuleOverview as OverviewRule,
  NotificationRuleDetail as DetailRule,
  NotificationRecipient as Recipient,
  NotificationRuleOwner as RuleOwner,
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
import { transformObject, transformObjects, pickMap } from '@/utilities/collection-util';

const ruleOverviewUrl = '/notification-rule-management/notification-rule'
export const fetchRuleOverview = (accessToken: string) => (
  doMock
    ? mockRequest(ruleOverviewUrl, mockedRuleOverview)
    : authApiRequest(ruleOverviewUrl, accessToken)
)

const ruleDetailUrl = (ruleId: number) => `/notification-rule-management/notification-rule/${ruleId}`
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
export const createNewRule = (accessToken: string, rule: object) => (
  doMock
    ? mockRequest(ruleDetailUrl(0), mockedRuleDetail(0))
    : authApiRequest(ruleCreationUrl, accessToken, {
      method: 'POST',
      body: JSON.stringify(rule)
    })
)

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
  recipients: object[]
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
      return { notificationCronTrigger: aggregator.value }
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
        value: apiAggregator.notificationCronTrigger
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

export const convertFromAPIRule = (rule: APIRule): DetailRule => transformObject(rule, {
  affectedFleets: 'fleets',
  affectingAllApplicableFleets: 'applyToAllFleets',
  aggregator: (apiAggregator: any) => (
    ['aggregator', convertAPIAggregator(apiAggregator as object)] as [string, Aggregator]
  ),
  owner: 'owner',
  recipients: 'recipients',
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
}, {
    dataSources: rule.condition.subConditions.map((subCondition) =>
      subCondition.providerName
    )
  }) as DetailRule

export const convertToAPIRule = (rule: DetailRule): APIRule => transformObject(rule, {
  fleets: (oldFleets: any) => (['affectedFleets', pickMap(oldFleets as Fleet[], 'fleetId')] as [string, object[]]),
  applyToAllFleets: 'affectingAllApplicableFleets',
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

/* BEGIN: Mocks */

const mockedRule: OverviewRule = {
  ruleId: 0,
  name: 'Status Reports',
  description: 'Rule Description for an examplary Notification Rule',
  aggregatorDescription: 'Sent every Tuesday, 9:00 AM',
  dataSources: [
    VehicleDataType.Engine,
    VehicleDataType.Battery
  ]
}

const mockedRule2: OverviewRule = {
  ...mockedRule,
  ruleId: 1,
  name: 'Engine and Fuel Alerts',
  aggregatorDescription: 'Sent Immediately',
  dataSources: [
    VehicleDataType.Fuel,
    VehicleDataType.Engine,
    VehicleDataType.Service
  ]
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
  recipients: [{ type: RecipientType.PhoneNumber, value: '+49 1234567890' }],
  condition: {
    logicalConnective: LogicalConnective.All,
    predicates: {}
  },
  aggregator: {
    strategy: AggregatorStrategy.Counting,
    value: 20
  }
})
