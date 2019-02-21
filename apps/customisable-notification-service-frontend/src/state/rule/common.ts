import { NotificationRuleInProgress } from '@/model/Rule'

export interface RuleModificationState {
  readonly inProgressRule: NotificationRuleInProgress
  readonly completedSteps: Set<number>,
  readonly currentStep: number
}
