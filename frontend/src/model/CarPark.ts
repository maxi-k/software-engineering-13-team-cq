export interface Fleet {
  fleetId: string,
  name: string,
  numVehicles: number
}

export interface CarPark {
  carParkId: string,
  name: string,
  admins: string[],
  customer: string,
  numUnassignedVehicles: number,
  fleets: Fleet[]
}
