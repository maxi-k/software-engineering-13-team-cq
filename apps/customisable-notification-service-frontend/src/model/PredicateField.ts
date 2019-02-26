export enum PredicateFieldType {
  TEXT = 'TEXT',
  DECIMAL = 'DECIMAL',
  INTEGER = 'INTEGER',
  WEEK = 'WEEK',
  DATE = 'DATE',
  STRING_LIST = 'STRING_LIST',
  LITER = 'LITER',
  HORSEPOWER = 'HORSEPOWER',
  CAPACITY = 'CAPACITY',
  HOUR = 'HOUR',
  DAY = 'DAY',
  PERCENTAGE_DECIMAL = 'PERCENTAGE_DECIMAL',
  PERCENTAGE_INT = 'PERCENTAGE_INT',
  VOLTAGE = 'VOLTAGE',
  KILOMETER_PER_WEEK = 'KILOMETER_PER_WEEK',
  KILOMETER = 'KILOMETER'
}

export interface PredicateField<T> {
  dataType: PredicateFieldType,
  fieldName: string,
  possibleEvaluationStrategies: string[]
}

export interface PredicateFieldText
  extends PredicateField<string> {
  dataType: PredicateFieldType.TEXT
}

export interface PredicateFieldDecimal
  extends PredicateField<number> {
  dataType: PredicateFieldType.DECIMAL
}

export interface PredicateFieldInteger
  extends PredicateField<number> {
  dataType: PredicateFieldType.INTEGER
}

export interface PredicateFieldWeek
  extends PredicateField<CalendarWeek> {
  dataType: PredicateFieldType.WEEK
}

export interface PredicateFieldDate
  extends PredicateField<Date> {
  dataType: PredicateFieldType.DATE
}

export interface PredicateFieldStringList
  extends PredicateField<string[]> {
  dataType: PredicateFieldType.STRING_LIST
}

export interface PredicateFieldLiter
  extends PredicateField<number> {
  dataType: PredicateFieldType.LITER
}

export interface PredicateFieldHorsepower
  extends PredicateField<number> {
  dataType: PredicateFieldType.HORSEPOWER
}

export interface PredicateFieldCapacity
  extends PredicateField<number> {
  dataType: PredicateFieldType.CAPACITY
}

export interface PredicateFieldHour
  extends PredicateField<Hour> {
  dataType: PredicateFieldType.HOUR
}

export interface PredicateFieldDay
  extends PredicateField<number> {
  dataType: PredicateFieldType.DAY
}

export interface PredicateFieldPercentageDecimal
  extends PredicateField<number> {
  dataType: PredicateFieldType.PERCENTAGE_DECIMAL
}

export interface PredicateFieldPercentageInt
  extends PredicateField<number> {
  dataType: PredicateFieldType.PERCENTAGE_INT
}

export interface PredicateFieldVoltage
  extends PredicateField<number> {
  dataType: PredicateFieldType.VOLTAGE
}

export interface PredicateFieldKilometerPerWeek
  extends PredicateField<number> {
  dataType: PredicateFieldType.KILOMETER_PER_WEEK
}

export interface PredicateFieldKilometer
  extends PredicateField<number> {
  dataType: PredicateFieldType.KILOMETER
}

// No, there's no prettier way to create
// a type for integer ranges. I checked.
export type CalendarWeek =
  1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13
  | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 | 23 | 24 | 25 | 26
  | 27 | 28 | 29 | 30 | 31 | 32 | 33 | 34 | 35 | 36 | 37 | 38 | 39
  | 40 | 41 | 42 | 43 | 44 | 45 | 46 | 47 | 48 | 49 | 50 | 51 | 52

export type Hour = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 0

// generated: [...Array(52)].map((_,i) => i).join(" | ")
