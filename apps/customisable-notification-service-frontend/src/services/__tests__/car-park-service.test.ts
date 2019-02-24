import fetchMock from 'fetch-mock'
import { CarPark, Fleet } from '@/model'
import {
  fetchCarParks,
  convertFromAPICarPark,
  mergeFleetInformation
} from "../car-park-service";
import mockedCarParks from '../mocks/mockedCarParks.json'
import mockedFleets from '../mocks/mockedFleets.json'

describe('The CarPark fetch functions', () => {
  it('fetchCarParks fetches the car parks from a backend', () => {
    fetchCarParks('authToken').then((result) => {
      expect(fetchMock.called(/car-parks/)).toBe(true)
      expect(result).toBe(mockedCarParks)
    })
  })
})

describe('the fleet merging function works', () => {
  const mockedFleetsByKey: { [key: string]: Fleet } = (mockedFleets as Fleet[]).reduce((fleetMap, fleet) => ({
    ...fleetMap,
    [fleet.fleetId]: fleet
  }), {})

  it('merges fleet data', () => {
    const fleetIds = [{
      "fleetId": "cccccccc-0000-ffff-0000-000000000099"
    }]

    const expectedFleets = [{
      "fleetId": "cccccccc-0000-ffff-0000-000000000099",
      "name": "Mother Of All Fleets",
      "numberOfVehicles": 5000
    }]

    expect(mergeFleetInformation(mockedFleetsByKey, fleetIds)).toEqual(expectedFleets)
  })

  it('returns empty fleets if none are selected', () => {
    expect(mergeFleetInformation(mockedFleetsByKey, [])).toEqual([])
  })

  it('filters out non-existent fleets', () => {
    const fleetIds = [{
      "fleetId": "cccccccc-0000-ffff-0000-000000000099"
    }]
    expect(mergeFleetInformation({}, fleetIds)).toEqual([])
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

    expect(convertedFleet.carParkId).toBe(convertedCarPark.carParkId)
    expect(convertedFleet.fleetId).toBe(apiFleet.id)
    expect(convertedFleet.name).toBe(apiFleet.name)
    expect(convertedFleet.numberOfVehicles).toBe(apiFleet.numVehicles)
  })
})
