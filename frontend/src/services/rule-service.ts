import { apiRequest } from '@/services/api-service'

const ruleOverviewUrl = '/notification-rule-management/notification-rule'
export const fetchRuleOverview = () => (
  apiRequest(ruleOverviewUrl)
)
