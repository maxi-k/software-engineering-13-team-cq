import {
  cronExpressionToStructuredCron,
  structuredCronToCronExpression,
  StructuredCron,
  stringToCronValue,
  isValidCronExpression
} from './cron-util'

describe('isValidCronExpression works', () => {

  it('returns true for invalid cron expressions', () => {
    expect(isValidCronExpression('* * ? * *')).toBe(true)
  })
  it('returns false for invalid cron expressions', () => {
    expect(isValidCronExpression('* * *')).toBe(false)
    expect(isValidCronExpression('invalid')).toBe(false)
  })
})

describe('stringToCronValue works', () => {
  it('parses possible strings correctly', () => {
    expect(stringToCronValue('*')).toBe('*')
    expect(stringToCronValue('0')).toBe(0)
    expect(stringToCronValue('35')).toBe(35)
    expect(stringToCronValue('1,2,3,4')).toEqual([1, 2, 3, 4])
    expect(stringToCronValue('45,90,10')).toEqual([45, 90, 10])
    expect(stringToCronValue('wrong')).toBe(undefined)
  })
})

describe('cronExpressionToStructuredCron and structuredCronToCronExpression work', () => {
  const defaultCron = '* * ? * *'
  it('parses standard wildcard cron expressions correctly', () => {
    const expectedValue: StructuredCron = {
      minute: '*',
      hour: '*',
      dayOfMonth: '?',
      month: '*',
      dayOfWeek: '*'
    }
    expect(cronExpressionToStructuredCron('* * ? * *', defaultCron)).toEqual(expectedValue)
    expect(structuredCronToCronExpression(expectedValue)).toEqual('* * * * *')
  })

  it('falls back to the default cron expression if the actual one is bad', () => {
    const expectedValue: StructuredCron = {
      minute: '*',
      hour: '*',
      dayOfMonth: '?',
      month: '*',
      dayOfWeek: '*'
    }
    expect(cronExpressionToStructuredCron('not cron!', defaultCron)).toEqual(expectedValue)
    expect(structuredCronToCronExpression(expectedValue)).toEqual('* * ? * *')
  })

  it('parses number cron expressions correctly', () => {
    const cronValue = '0 1 5 * 0'
    const expectedValue: StructuredCron = {
      minute: 0,
      hour: 1,
      dayOfMonth: 5,
      month: '*',
      dayOfWeek: 0
    }
    expect(cronExpressionToStructuredCron(cronValue, defaultCron)).toEqual(expectedValue)
    expect(structuredCronToCronExpression(expectedValue)).toEqual(cronValue)
  })

  it('parses multivalue cron expressions correctly', () => {
    const cronValue = '0 1 5 * 1,2,3,4,5'
    const expectedValue: StructuredCron = {
      minute: 0,
      hour: 1,
      dayOfMonth: 5,
      month: '*',
      dayOfWeek: [1, 2, 3, 4, 5]
    }
    expect(cronExpressionToStructuredCron(cronValue, defaultCron)).toEqual(expectedValue)
    expect(structuredCronToCronExpression(expectedValue)).toEqual(cronValue)
  })
})
