export type CronValue = number | '*' | number[]

export interface StructuredCron {
  minute: CronValue
  hour: CronValue
  dayOfMonth: CronValue
  month: CronValue
  dayOfWeek: CronValue
}

export const isValidCronExpression = (expression: string): boolean => {
  const parts = expression.split(' ')
  return parts.length >= 5
}

export const cronValueToString = (cronValue: CronValue): string => {
  switch (typeof cronValue) {
    // cron value is a list of numbers
    case 'object':
      return cronValue.join(',')
    // cron value is *
    case 'string':
    // cron value is a single number
    case 'number':
    default:
      return cronValue.toString()
  }
}

export const stringToCronValue = (expression: string): CronValue | undefined => {
  if (expression.includes(',')) {
    return expression
      .split(',')
      .map((numberString) => parseInt(numberString, 10))
      .filter((numberOrNaN) => !isNaN(numberOrNaN))
  }
  if (expression === '*') {
    return expression
  }
  const parsed = parseInt(expression, 10)
  return isNaN(parsed) ? undefined : parsed
}

export const cronExpressionToStructuredCron = (expression: string, defaultExpression: string): StructuredCron => {
  const cron = isValidCronExpression(expression) ? expression : defaultExpression
  const [minute, hour, dayOfMonth, month, dayOfWeek] = cron.split(' ')
    .map(stringToCronValue)
    .map((cronValue) => typeof cronValue === 'undefined' ? '*' : cronValue)
  return { minute, hour, dayOfMonth, month, dayOfWeek }
}

export const structuredCronToCronExpression = (structuredCron: StructuredCron): string => {

  const { minute, hour, dayOfMonth, month, dayOfWeek } = structuredCron
  const minuteString = cronValueToString(minute)
  const hourString = cronValueToString(hour)
  const dayOfMonthString = cronValueToString(dayOfMonth)
  const monthString = cronValueToString(month)
  const dayOfWeekString = cronValueToString(dayOfWeek)

  return `${minuteString} ${hourString} ${dayOfMonthString} ${monthString} ${dayOfWeekString}`
}
