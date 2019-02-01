import styled from 'styled-components'
import { SFC, HTMLAttributes } from 'react'
import { RuleCreationState } from '@/state/rule'

export interface CommonRuleCreationStateAttributes
  extends Pick<RuleCreationState, 'inProgressRule'> {

}

export type FieldKey = string | number
export type FieldUpdater = (name: FieldKey, callback: ((...event: any[]) => any)) => ((...value: any[]) => void)
export interface CommonRuleCreationDispatchAttributes {
  updateField: FieldUpdater
}

export type RuleCreationStepViewProps =
  HTMLAttributes<HTMLDivElement>
  & CommonRuleCreationStateAttributes
  & CommonRuleCreationDispatchAttributes

export type RuleCreationStepView = SFC<RuleCreationStepViewProps>

export type FieldUpdateCreator = (updater: FieldUpdater) => (name: FieldKey) => ((value: any) => void)
export const createInputFieldUpdater: FieldUpdateCreator = updater => name => (
  updater(name, (event: React.ChangeEvent<HTMLInputElement>) => (
    { $set: event.target.value }
  ))
)

export const createCheckboxUpdater: FieldUpdateCreator = updater => name => (
  updater(name, (event: React.ChangeEvent<HTMLInputElement>) => (
    { $set: event.target.checked }
  ))
)

export const createMultiValueUpdater: FieldUpdateCreator = updater => name => (
  updater(name, <T extends { value: any }>(selected: T[], newlySelected: T) => (
    { $set: selected.map((entry) => entry.value) }
  ))
)

export const createSetMultiValueUpdater: FieldUpdateCreator = updater => name => (
  updater(name, <T extends { value: any }>(action: '$add' | '$remove', newlySelected: T) => (
    { [action]: [newlySelected.value] }
  ))
)

export const createSingleValueUpdater: FieldUpdateCreator = updater => name => (
  updater(name, <T extends { value: any }>(selected: T, previouslySelected: T) => (
    { $set: selected.value }
  ))
)

export const createMergingValueUpdater: FieldUpdateCreator = updater => name => (
  updater(name, <T extends { value: any }>(toAdd: Record<any, T>) => (
    { $merge: toAdd }
  ))
)

export const createRemovingValueUpdater: FieldUpdateCreator = updater => name => (
  updater(name, (toRemove: string[]) => (
    { $unset: toRemove }
  ))
)

export const nestValueUpdater = (updater: FieldUpdater) => (outerName: FieldKey): FieldUpdater => (
  (innerName, callback) => (
    updater(outerName, (...event: any[]) => ({ [innerName]: callback(...event) }))
  )
)

export const StyledRuleCreationWrapper = styled.div`
    padding: 0rem 5rem;
`