import {
  LogicalConnective,
  AggregatorStrategy,
  NotificationRuleInProgress
} from '@/model/Rule'

export interface RuleModificationState {
  readonly inProgressRule: NotificationRuleInProgress
  readonly completedSteps: Set<number>,
  readonly currentStep: number,
  readonly ruleErrors: { [key: string]: string }
}

export const initialModificationState = {
  inProgressRule: {
    name: "",
    description: "",
    recipients: [],
    applyToAllFleets: true,
    ownerAsAdditionalRecipient: true,
    fleets: [],
    aggregator: {
      strategy: AggregatorStrategy.Immediate
    },
    condition: {
      logicalConnective: LogicalConnective.Any,
      predicates: {},
    }
  },
  completedSteps: new Set(),
  currentStep: 0,
  ruleErrors: {}
}
