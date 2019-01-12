import { HTMLAttributes } from 'react'
import { FetchError } from './Status'

export interface FetchingAttributes {
  isFetching: boolean,
  hasFetchError: FetchError
}
export type FetchingData = Readonly<FetchingAttributes>

export type BasicHTMLProps = HTMLAttributes<HTMLDivElement>
export type HTMLProps<T> = HTMLAttributes<T>

export interface SelectValue {
  label: string,
  value: any
}
