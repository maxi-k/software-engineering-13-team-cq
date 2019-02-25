export const applyLazy = <I, O>(functionToApply: ((input: I) => O), value: I): (() => O) => (
  () => functionToApply(value)
)

export const printingArguments = <I, O>(functionToApply: ((input: I) => O)): ((input: I) => O) => (
  (actualInput: I) => {
    // tslint:disable-next-line:no-console
    console.info(actualInput)
    return functionToApply(actualInput)
  }
)

export const mapArguments = <I, N, O>(
  functionToApply: ((input: I) => O),
  mapperFunction: ((newInput: N) => I)
): ((newInput: N) => O) => (
    (actualInput: N) => functionToApply(mapperFunction(actualInput))
  )

export const identity = <T>(value: T): T => value
