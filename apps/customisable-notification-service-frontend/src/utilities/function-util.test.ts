import {
  applyLazy,
  mapArguments,
  printingArguments,
  identity
} from './function-util'

it('printingArguments does not change the outcome of a function', () => {
  const function1 = (value: number) => value * 2
  expect(printingArguments(function1)(10)).toBe(function1(10))
})

it('mapArguments transforms function arguments', () => {
  const function1 = (value: number) => value * 2
  const transformedFunction = mapArguments(function1, (argument: number) => argument * 3)
  expect(transformedFunction(10)).toBe(function1(3 * 10))
})

it('identity is the identity function', () => {
  expect(identity(10)).toBe(10)
  expect(identity('hello')).toBe('hello')
})

it('applyLazy lazily applies the function to its arguments', () => {
  let applied = false
  const function1 = (value: number) => {
    applied = true
    return value * 2
  }
  const lazyFunction = applyLazy(function1, 10)
  expect(applied).toBe(false)
  expect(lazyFunction()).toBe(20)
  expect(applied).toBe(true)
})
