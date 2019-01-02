import { HTMLAttributes } from 'react'

export interface FetchingAttributes {
  isFetching: boolean,
  hasFetchError: boolean
}

export type BasicHTMLProps = HTMLAttributes<HTMLDivElement>
export type HTMLProps<T> = HTMLAttributes<T>
