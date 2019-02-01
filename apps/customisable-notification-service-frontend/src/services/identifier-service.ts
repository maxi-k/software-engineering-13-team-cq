// TODO: check how big this library is
// when compiled for production.
import Chance from 'chance'

const chance = new Chance()

export const createRandomKey = (): string => (
  chance.guid()
)
