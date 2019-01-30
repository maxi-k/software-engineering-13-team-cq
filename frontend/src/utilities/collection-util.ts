import update from 'immutability-helper'

export const pluck = <T, K extends keyof T>(o: T, names: K[]): Array<T[K]> => (
  names.map(n => o[n])
)

export const pick = <T, K extends keyof T>(object: T, ...names: K[]): Pick<T, K> => (
  names.reduce((newObj, key) => (
    { ...newObj, [key]: object[key] }
  ), {}) as Pick<T, K>
)
export const pickMap = <T, K extends keyof T>(objects: T[], ...names: K[]): Array<Pick<T, K>> => (
  objects.map((entry: T) => pick(entry, ...names))
)

export type ValueOf<ObjectType> = ObjectType[keyof ObjectType]
export type KeyValueMapper<ObjectType, ResultType> = (key: keyof ObjectType, value: ValueOf<ObjectType>) => ResultType

export const mapObjectToArray = <ObjectType, ResultType>(
  enumeration: ObjectType,
  callback: KeyValueMapper<ObjectType, ResultType>): ResultType[] => (
    Object.keys(enumeration).map((key: string) => (
      callback(key as keyof ObjectType,
        enumeration[key as keyof ObjectType])
    ))
  )

export type ObjectKeyMapper<ValueType> =
  string
  | ((value: ValueType) => [string, any])

export const transformObject = <ObjectType>(
  oldObject: ObjectType,
  transformations: Partial<Record<keyof ObjectType, ObjectKeyMapper<ObjectType[keyof ObjectType]>>>
): object => (
    Object.keys(oldObject).reduce((result: object, objectStringKey: string) => {
      const objectKey = objectStringKey as keyof ObjectType
      const objectValue = oldObject[objectKey] as ObjectType[keyof ObjectType]
      const valueTransformation: ObjectKeyMapper<typeof objectValue> | undefined = transformations[objectKey]
      if (typeof valueTransformation === 'undefined' || valueTransformation === null) {
        return update(result, { [objectKey]: { $set: objectValue } })
      } else if (typeof valueTransformation === 'string') {
        return update(result, { [valueTransformation]: { $set: objectValue } })
      } else {
        const [transformedKey, transformedValue] = valueTransformation(objectValue)
        return update(result, { [transformedKey]: { $set: transformedValue } })
      }
    }, {})
  )
