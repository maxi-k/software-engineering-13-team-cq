import update from 'immutability-helper'
import { fleetdataUrl as carParkServiceUrl } from './api-service'

import { authApiRequest, mockRequest, doMock } from '@/services/api-service'
import { Fleet, CarPark } from '@/model'
import { transformObject } from '@/utilities/collection-util';

export interface CarParkAPIResponse {
  items: CarPark[]
}

const fetchCarParksPath = '/api/car-parks'
export const fetchCarParks = (accessToken: string) => (
  doMock
    ? mockRequest(fetchCarParksPath, require('./mocks/mockedCarParks.json'))
    : authApiRequest(fetchCarParksPath, accessToken, {
      urlPrefix: carParkServiceUrl
    })
)

export const convertFromAPICarPark = (apiCarPark: object): CarPark => transformObject(apiCarPark, {
  id: 'carParkId',
  numVehicles: 'numberOfVehicles',
  numUnassignedVehicles: 'numberOfUnassignedVehicles',
  fleets: (apiFleets: any, enclosingObject: any) => [
    'fleets',
    apiFleets.map((fleet: object) => transformObject(fleet, {
      id: 'fleetId',
      numVehicles: 'numberOfVehicles'
    }, {
        carParkId: enclosingObject.id
      }))] as [string, Fleet[]]
}) as CarPark

export const extractFleets = (carParks: CarPark[]): { [key: string]: Fleet } => (
  carParks.reduce((fleets, park) => (
    update(fleets, {
      $add: park.fleets.map((fleet) => ([fleet.fleetId, fleet]))
    })), {})
)
