import update from 'immutability-helper'
import { isDevelopment } from './environment-service'

import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { Fleet, CarPark } from '@/model'
import { transformObject } from '@/utilities/collection-util';

export interface CarParkAPIResponse {
  items: CarPark[]
}

const carParkServiceUrl = isDevelopment
  ? '' // On Development, it's local (see proxy paths from /setupProxy.js)
  : '' // TODO: Insert production url for fleet service

const fetchCarParksUrl = carParkServiceUrl + '/api/car-parks'
export const fetchCarParks = (accessToken: string) => (
  doMock
    ? mockRequest(fetchCarParksUrl, require('./mocks/mockedCarParks.json'))
    : authApiRequest(fetchCarParksUrl, accessToken)
)

export const convertFromAPICarPark = (apiCarPark: object): CarPark => transformObject(apiCarPark, {
  id: 'carParkId',
  fleets: (apiFleets: any) => ['fleets', apiFleets.map((fleet: object) => transformObject(fleet, {
    id: 'fleetId'
  }))] as [string, Fleet[]]
}) as CarPark

export const extractFleets = (carParks: CarPark[]): { [key: string]: Fleet } => (
  carParks.reduce((fleets, park) => (
    update(fleets, {
      $add: park.fleets.map((fleet) => ([fleet.fleetId, fleet]))
    })), {})
)
