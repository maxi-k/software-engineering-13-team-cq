import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { capitalizeString } from '@/utilities/string-util'
import {
  NotificationRuleOverview as OverviewRule,
  NotificationRuleDetail as DetailRule,
  NotificationRecipient as Recipient,
  NotificationRecipientType as RecipientType,
  LogicalConnective,
  VehicleDataType,
  AggregatorStrategy,
  Aggregator,
  Fleet
} from '@/model'
import { transformObject, pickMap } from '@/utilities/collection-util';

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
  ruleId: number,
  name: string,
  description: string,
  owner: {
    name: string,
    mailAddress: string,
    cellPhoneNumber: string,
    userSettings: {
      userNotificationType: 'EMAIL' | 'SMS'
    },
  }
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

export const convertToAPIRule = (rule: DetailRule): object => ({
  ownerAsAdditionalRecipient: true,
  ...transformObject(rule, {
    fleets: (oldFleets: any) => (['affectedFleets', pickMap(oldFleets as Fleet[], 'fleetId')] as [string, object[]]),
    applyToAllFleets: 'affectingAllApplicableFleets',
    condition: (oldCondition: any) => (['condition', {
      '@type': 'RuleConditionCompositeDto',
      ...transformObject(oldCondition, {
        logicalConnective: (connective: any) => (['logicalConnective', connective.toUpperCase()] as [string, string]),
        predicates: (predicates: any) => (['predicates', Object.values(predicates)] as [string, string[]])
      })
    }] as [string, object]),
    aggregator: (aggregator: any) => (['aggregator', {
      '@type': convertAggregatorType(aggregator.strategy),
      ...convertAggregatorValueKey(aggregator as Aggregator)
    }] as [string, object])
  })
})

const createRecipient = (rule: APIRule): Recipient => (
  rule.owner ?
    {
      type: capitalizeString(rule.owner
        ? rule.owner.userSettings.userNotificationType
        : RecipientType.Email
      ) as RecipientType,
      value: `OWNER::${rule.owner.mailAddress}/${rule.owner.cellPhoneNumber}`
    } : {
      type: RecipientType.Email,
      value: 'lol@lol.com'
    }
)

export const mergeMockedRuleData = (rule: APIRule): DetailRule => (
  {
    ruleId: rule.ruleId,
    name: rule.name || 'MOCKED',
    description: rule.description || 'MOCKED',
    aggregatorDescription: 'MOCKED',
    applyToAllFleets: false,
    fleets: [
      { name: 'MOCKED FLEET', fleetId: 'mockedFleet1', numVehicles: 42 }
    ],
    dataSources: [
      VehicleDataType.Engine,
      VehicleDataType.Battery,
      VehicleDataType.Service,
    ],
    recipients: [createRecipient(rule)],
    condition: {
      logicalConnective: LogicalConnective.Any,
      predicates: {}
    },
    aggregator: {
      strategy: AggregatorStrategy.Immediate
    }
  })

// export const mergeMockedRuleCreateData = (rule: DetailRule): APIRule => ({
//   name: 'MOCKED NAME',
//   description: 'MOCKED DESCRIPTION',
//
//   ...rule
// })

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

const mockedRuleOverview: OverviewRule[] = [mockedRule, mockedRule2]

const mockedRuleDetail = (ruleId: number): DetailRule => ({
  ruleId,
  ...mockedRuleOverview[ruleId % mockedRuleOverview.length],
  applyToAllFleets: true,
  fleets: [],
  recipients: [{ type: RecipientType.Email, value: 'maxi.kuschewski@gmail.com' },
  { type: RecipientType.PhoneNumber, value: '+49 1234567890' }],
  condition: {
    logicalConnective: LogicalConnective.All,
    predicates: {}
  },
  aggregator: {
    strategy: AggregatorStrategy.Counting,
    value: 20
  }
})
