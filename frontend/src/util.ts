const mapObject = <T>(object: T, mapper(key: keyof T, value: T[keyof T])) => (
  Object.keys(object).map((key) => mapper(key, object[key]))
)

export {
  mapObject
}
