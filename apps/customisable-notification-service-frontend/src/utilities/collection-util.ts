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

export type ObjectKeyMapper<ObjectType, ValueType = ObjectType[keyof ObjectType]> =
  string
  | Array<((value: ValueType, object: ObjectType) => [string, any])
            | ((value: ValueType) => [string, any])>
  | ((value: ValueType) => [string, any])
  | ((value: ValueType, object: ObjectType) => [string, any])

const generateTransformation = <ObjectType, ValueType>(
  valueTransformation: ObjectKeyMapper<ObjectType, ValueType>,
  objectValue: ValueType,
  enclosingObject: ObjectType
): object => {
  switch (typeof valueTransformation) {
    case 'function':
      const [transformedKey, transformedValue] = valueTransformation(objectValue, enclosingObject)
      return { [transformedKey]: { $set: transformedValue } }
    case 'object':
      if (Array.isArray(valueTransformation)) {
        return valueTransformation.reduce((result, entry) => (
          { ...result, ...generateTransformation(entry, objectValue, enclosingObject) }
        ), {})
      } else {
        return Object.keys(valueTransformation).reduce((result, key) => (
          {
            ...result, [key]: generateTransformation(
              valueTransformation[key],
              (objectValue as { [key: string]: any })[key],
              objectValue
            )
          }
        ), {})
      }
    case 'string':
    default:
      return { [valueTransformation]: { $set: objectValue } }
  }
}

export const transformObject = <ObjectType>(
  oldObject: ObjectType,
  transformations: Partial<Record<keyof ObjectType, ObjectKeyMapper<ObjectType>>>,
  initializationObject: object = {}
): object => (
    Object.keys(oldObject).reduce((result: object, objectStringKey: string) => {
      const objectKey = objectStringKey as keyof ObjectType
      const objectValue = oldObject[objectKey] as ObjectType[keyof ObjectType]
      const valueTransformation: ObjectKeyMapper<ObjectType, typeof objectValue> | undefined = transformations[objectKey]

      if (typeof valueTransformation === 'undefined' || valueTransformation === null) {
        return update(result, { [objectKey]: { $set: objectValue } })
      } else {
        return update(result, generateTransformation(valueTransformation, objectValue, oldObject))
      }
    }, initializationObject)
  )

export const transformObjects = <ObjectType>(
  oldObjects: ObjectType[],
  transformations: Partial<Record<keyof ObjectType, ObjectKeyMapper<ObjectType>>>,
  initializationObject: object = {}
) => (
    oldObjects.map((object: ObjectType) => transformObject(
      object, transformations, initializationObject
    ))
  )
