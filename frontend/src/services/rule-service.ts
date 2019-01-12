import { apiRequest, mockRequest, doMock } from '@/services/api-service'
import { NotificationRuleOverview as OverviewRule,
         NotificationRuleDetail as DetailRule,
         NotificationRecipientType as RecipientType,
         VehicleDataType
} from '@/model'

const ruleOverviewUrl = '/notification-rule-management/notification-rule'
export const fetchRuleOverview = () => (
  doMock
  ? apiRequest(ruleOverviewUrl)
  : mockRequest(ruleOverviewUrl, mockedRuleOverview)
)

const ruleDetailUrl = (ruleId: number) => `/notification-rule-management/notification-rule/${ruleId}`
export const fetchRuleDetail = (ruleId: number) => (
  doMock
  ? apiRequest(ruleDetailUrl(ruleId))
  : mockRequest(ruleDetailUrl(ruleId), mockedRuleDetail(ruleId))
)

const mockedRule: OverviewRule = {
  id: 1,
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
  id: 2,
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
  id: ruleId,
  ...mockedRuleOverview[ruleId % mockedRuleOverview.length],
  recipients: [{ type: RecipientType.Email, value: 'maxi.kuschewski@gmail.com'},
               { type: RecipientType.PhoneNumber, value: '+49 1234567890' }],
})
