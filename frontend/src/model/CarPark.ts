export interface Fleet {
  id: string,
  name: string,
  numVehicles: number
}

export interface CarPark {
  id: string,
  name: string,
  admins: string[],
  customer: string,
  numUnassignedVehicles: number,
  fleets: Fleet[]
}
