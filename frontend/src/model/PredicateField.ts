export enum PredicateFieldType {
  Text = 'text',
  Decimal = 'decimal',
  Integer = 'integer',
  Week = 'week',
  Date = 'date',
  StringList = 'stringList',
}

export interface PredicateField<T> {
  dataType: PredicateFieldType,
  fieldName: string,
  possibleEvaluationStrategies: string[]
}

export interface PredicateFieldText
  extends PredicateField<string> {
  dataType: PredicateFieldType.Text
}

export interface PredicateFieldDecimal
  extends PredicateField<number> {
  dataType: PredicateFieldType.Decimal
}

export interface PredicateFieldInteger
  extends PredicateField<number> {
  dataType: PredicateFieldType.Integer
}

export interface PredicateFieldWeek
  extends PredicateField<CalendarWeek> {
  dataType: PredicateFieldType.Week
}

export interface PredicateFieldDate
  extends PredicateField<Date> {
  dataType: PredicateFieldType.Date
}

export interface PredicateFieldStringList
  extends PredicateField<string[]> {
  dataType: PredicateFieldType.StringList
}

// No, there's no prettier way to create
// a type for integer ranges. I checked.
export type CalendarWeek = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 | 23 | 24 | 25 | 26 | 27 | 28 | 29 | 30 | 31 | 32 | 33 | 34 | 35 | 36 | 37 | 38 | 39 | 40 | 41 | 42 | 43 | 44 | 45 | 46 | 47 | 48 | 49 | 50 | 51 | 52
// generated: [...Array(52)].map((_,i) => i).join(" | ")
