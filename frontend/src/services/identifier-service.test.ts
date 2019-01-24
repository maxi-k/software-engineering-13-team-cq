import { createUUID } from './identifier-service'

it('generates unique uuids', () => {
  expect(createUUID()).not.toBe(createUUID())
})
