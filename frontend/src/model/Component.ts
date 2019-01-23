import { HTMLAttributes } from 'react'
import { FetchError } from './Status'
import { ValueType, ActionMeta, InputActionMeta } from 'react-select/lib/types'

export interface FetchingAttributes {
  isFetching: boolean,
  hasFetchError: FetchError
}
export type FetchingData = Readonly<FetchingAttributes>

export type BasicHTMLProps = HTMLAttributes<HTMLDivElement>
export type HTMLProps<T> = HTMLAttributes<T>
export type KeyType = string | number | symbol

export interface SelectValue {
  key?: KeyType,
  label: string,
  value: any
}

export interface SelectFormattedValue {
  key?: KeyType,
  label: React.ReactNode,
  value: any
}

export type SelectOnChangeType<SelectValueType> = (
  value: ValueType<SelectValueType>,
  action: ActionMeta | InputActionMeta
) => void
