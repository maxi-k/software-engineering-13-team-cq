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
  name: 'Rule Name',
  description: 'Rule Description for an examplary Rule',
  aggregatorDescription: 'Sent every Tuesday, 9:00 AM',
  dataSources: [
    VehicleDataType.Engine,
    VehicleDataType.Battery
  ]
}

const mockedRuleDetail = (ruleId: number): DetailRule => ({
  ...mockedRule,
  id: ruleId,
  recipients: [{ type: RecipientType.Email, value: 'maxi.kuschewski@gmail.com'},
               { type: RecipientType.PhoneNumber, value: '+49 1234567890' }],
})

const mockedRuleOverview: OverviewRule[] = [mockedRule, { ...mockedRule, id: 2, name: 'Rule Name 2' }]
