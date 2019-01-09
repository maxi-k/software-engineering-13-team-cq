import { apiRequest, mockRequest, doMock } from '@/services/api-service'
import { NotificationRuleOverview as OverviewRule, VehicleDataType } from '@/model'

const ruleOverviewUrl = '/notification-rule-management/notification-rule'
export const fetchRuleOverview = () => (
  doMock
  ? apiRequest(ruleOverviewUrl)
  : mockRequest(ruleOverviewUrl, mockedRuleOverview)
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

const mockedRuleOverview: OverviewRule[] = [mockedRule, { ...mockedRule, id: 2, name: 'Rule Name 2' }]
