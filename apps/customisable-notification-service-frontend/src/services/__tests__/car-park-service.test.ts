import fetchMock from 'fetch-mock'
import { fetchCarParks, convertFromAPICarPark } from "../car-park-service";
import mockedCarPark from '../mocks/mockedCarParks.json'

describe('The CarPark fetch functions', () => {
  it('fetchCarParks fetches the car parks from a backend', () => {
    fetchCarParks('authToken').then((result) => {
      expect(fetchMock.called(/car-parks/)).toBe(true)
      expect(result).toBe(mockedCarPark)
    })
  })

})

describe('the conversion function works', () => {
  it('convertFromAPICarPark converts a given API car park', () => {
    expect(convertFromAPICarPark(mockedCarPark)).not.toBe(null)
  })
})
