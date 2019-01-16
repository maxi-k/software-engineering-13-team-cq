import update from 'immutability-helper'
import { isDevelopment } from './environment-service'

import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { Fleet, CarPark } from '@/model'

export interface CarParkAPIResponse {
  items: CarPark[]
}

export const extractFleets = (carParks: CarPark[]): { [key: string]: Fleet } => (
  carParks.reduce((fleets, park) => (
    update(fleets, {
      $add: park.fleets.map((fleet) => ([fleet.id, fleet]))
    })), {})
)

const carParkServiceUrl = isDevelopment
  ? '' // On Development, it's local (see proxy paths from /setupProxy.js)
  : '' // TODO: Insert production url for fleet service

const fetchCarParksUrl = carParkServiceUrl + '/api/car-parks'
export const fetchCarParks = (accessToken: string) => (
  doMock
    ? mockRequest(fetchCarParksUrl, require('./mocks/mockedCarParks.json'))
    : authApiRequest(fetchCarParksUrl, accessToken)
)
