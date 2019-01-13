import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { NotificationRuleOverview as OverviewRule,
         NotificationRuleDetail as DetailRule,
         NotificationRecipientType as RecipientType,
         VehicleDataType
} from '@/model'

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
  recipients: [{ type: RecipientType.Email, value: 'maxi.kuschewski@gmail.com'},
               { type: RecipientType.PhoneNumber, value: '+49 1234567890' }],
})
