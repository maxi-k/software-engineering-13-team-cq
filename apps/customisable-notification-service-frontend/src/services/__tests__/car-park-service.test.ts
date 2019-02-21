import fetchMock from 'fetch-mock'
import { CarPark } from '@/model'
import { fetchCarParks, convertFromAPICarPark } from "../car-park-service";
import mockedCarParks from '../mocks/mockedCarParks.json'

describe('The CarPark fetch functions', () => {
  it('fetchCarParks fetches the car parks from a backend', () => {
    fetchCarParks('authToken').then((result) => {
      expect(fetchMock.called(/car-parks/)).toBe(true)
      expect(result).toBe(mockedCarParks)
    })
  })
})

describe('the conversion function works', () => {
  it('convertFromAPICarPark converts a given API car park', () => {
    const apiCarPark = mockedCarParks.items[0]
    const convertedCarPark: CarPark = convertFromAPICarPark(apiCarPark)

    expect(convertedCarPark).not.toBe(null)

    expect(convertedCarPark.carParkId).toBe(apiCarPark.id)
    expect(convertedCarPark.name).toBe(apiCarPark.name)
    expect(convertedCarPark.customer).toBe(apiCarPark.customer)
    expect(convertedCarPark.numberOfVehicles).toBe(apiCarPark.numVehicles)
    expect(convertedCarPark.numberOfUnassignedVehicles).toBe(apiCarPark.numUnassignedVehicles)
    expect(convertedCarPark.admins).toEqual(apiCarPark.admins)

    const apiFleet = apiCarPark.fleets[0]
    const convertedFleet = convertedCarPark.fleets[0]

    expect(convertedFleet.fleetId).toBe(apiFleet.id)
    expect(convertedFleet.name).toBe(apiFleet.name)
    expect(convertedFleet.numberOfVehicles).toBe(apiFleet.numVehicles)
  })
})
