import { createRandomKey } from './identifier-service'

it('generates unique uuids', () => {
  expect(createRandomKey()).not.toBe(createRandomKey())
})
