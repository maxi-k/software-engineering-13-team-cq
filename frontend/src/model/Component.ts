import { HTMLAttributes } from 'react'

export interface FetchingAttributes {
  isFetching: boolean,
  hasFetchError: boolean | Error | string
}
export type FetchingData = Readonly<FetchingAttributes>

export type BasicHTMLProps = HTMLAttributes<HTMLDivElement>
export type HTMLProps<T> = HTMLAttributes<T>

export interface SelectValue {
  label: any,
  value: any
}
