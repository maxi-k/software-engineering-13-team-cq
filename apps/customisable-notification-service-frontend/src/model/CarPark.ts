export interface Fleet {
  fleetId: string,
  name: string,
  numberOfVehicles: number
}

export interface CarPark {
  carParkId: string,
  name: string,
  admins: string[],
  customer: string,
  numberOfUnassignedVehicles: number,
  numberOfVehicles: number,
  fleets: Fleet[]
}
