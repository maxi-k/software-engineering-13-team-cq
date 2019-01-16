import styled from 'styled-components'
import { SFC, HTMLAttributes } from 'react'
import { RuleCreationState } from '@/state/rule'

export interface CommonRuleCreationStateAttributes
  extends Pick<RuleCreationState, 'inProgressRule'> {

}

export type FieldUpdater = (name: string, callback: ((event: any) => any)) => ((value: any) => void)
export interface CommonRuleCreationDispatchAttributes {
  updateField: FieldUpdater
}

export type RuleCreationStepViewProps =
  HTMLAttributes<HTMLDivElement>
  & CommonRuleCreationStateAttributes
  & CommonRuleCreationDispatchAttributes

export type RuleCreationStepView = SFC<RuleCreationStepViewProps>

export const createInputFieldUpdater = (updater: FieldUpdater, name: string): ((value: any) => void) => (
  updater(name, (event: React.ChangeEvent<HTMLInputElement>) => (
    { $set: event.target.value }
  ))
)

export const StyledRuleCreationWrapper = styled.div`
    padding: 0rem 5rem;
`
