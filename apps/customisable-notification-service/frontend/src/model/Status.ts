export interface StatusObject {
  status: number,
  [key: string]: any
}
export type FetchError = boolean | Error | string | StatusObject
