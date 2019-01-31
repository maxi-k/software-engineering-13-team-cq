import update from 'immutability-helper'
import { fleetdataUrl as carParkServiceUrl } from './api-service'

import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { Fleet, CarPark } from '@/model'
import { transformObject } from '@/utilities/collection-util';

export interface CarParkAPIResponse {
  items: CarPark[]
}

const fetchCarParksUrl = carParkServiceUrl + '/api/car-parks'
export const fetchCarParks = (accessToken: string) => (
  doMock
    ? mockRequest(fetchCarParksUrl, require('./mocks/mockedCarParks.json'))
    : authApiRequest(fetchCarParksUrl, accessToken)
)

export const convertFromAPICarPark = (apiCarPark: object): CarPark => transformObject(apiCarPark, {
  id: 'carParkId',
  numUnassignedVehicles: 'numberOfUnassignedVehicles',
  fleets: (apiFleets: any) => ['fleets', apiFleets.map((fleet: object) => transformObject(fleet, {
    id: 'fleetId',
    numVehicles: 'numberOfVehicles'
  }))] as [string, Fleet[]]
}) as CarPark

export const extractFleets = (carParks: CarPark[]): { [key: string]: Fleet } => (
  carParks.reduce((fleets, park) => (
    update(fleets, {
      $add: park.fleets.map((fleet) => ([fleet.fleetId, fleet]))
    })), {})
)
