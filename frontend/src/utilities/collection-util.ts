export type ValueOf<ObjectType> = ObjectType[keyof ObjectType]
export type KeyValueMapper<ObjectType, ResultType> = (key: keyof ObjectType, value: ValueOf<ObjectType>) => ResultType

export const mapObjectToArray = <ObjectType, ResultType>(
  enumeration: ObjectType,
  callback: KeyValueMapper<ObjectType, ResultType>): ResultType[] => (
    Object.keys(enumeration).map((key: string) => (
      callback(key as keyof ObjectType, enumeration[key as keyof ObjectType])
    ))
  )
