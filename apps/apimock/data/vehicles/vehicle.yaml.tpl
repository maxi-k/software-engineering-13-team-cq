_times_: 5000
vin: WBAXXX#{format('%011d',#i)}
carPark: cccccccc-0000-cccc-0000-000000000099
fleet: cccccccc-0000-ffff-0000-000000000099
brand: BMW
model: 740Le iPerformance
series: '7'
licensePlate: B-MW #{format('%04d',#i)}
note: "generated vehicle"
image: /img/vehicles/cccccccc-0000-eeee-9999-#{format('%012d',#i)}
contract:
  endDate: '2020-08-24'
  startDate: '2018-09-27'
  endMileage:  #{random(10001, 40000)}
  startMileage:  #{random(0, 10000)}
  reachedRuntimePercentage: #{random(0, 130)}
variant:
  color: BLACK SAPPHIRE METALLIC
  doors: 4
  steering: LL
  extraEquipment:
  - saCode: '0534'
  - saCode: '0609'
  - saCode: 06AE
  - saCode: 06AC
  - saCode: 06AK
  - saCode: 06AM
  - saCode: 06AN
  - saCode: 06AP
  - saCode: 06WD
engine:
  power:  #{random(50, 1000)}
  capacity:  #{random(1000, 10000)}
  fuelType: DDE
battery:
  level: 3.1
  voltage: 14.84
  chargingStatus: NOCHARGING
  remainingChargingHours: 3
  remainingRange: 30
fuel:
  level: 63.2
  currentLiters: 43
  tankCapacity: #{random(20, 120)}
  remainingRange: 500
mileage:
  current:  #{random(10001, 30000)}
  averagePerWeek: #{random(0, 1000)}
  forecastEndContract: 20345
  expectedExceedance: 345
  reached: #{random(0, 150)}
  status: #{random(0, 500) == 0 ? 'CRITICAL' : random(0, 500) == 0 ? 'WARNING' : 'OK'}
serviceStatus:
  vin: WBA7J0102HGJ36060
  status: #{random(0, 50) == 0 ? 'CRITICAL' : random(0, 50) == 0 ? 'WARNING' : 'OK'}
  dueDate: 2019-04
  remainingDays: 180
  remainingMileage: 11000
  frontBrake:
    id: '00002'
    status: #{random(0, 100) == 0 ? 'CRITICAL' : random(0, 100) == 0 ? 'WARNING' : 'OK'}
  rearBrake:
    id: '00006'
    status: #{random(0, 100) == 0 ? 'CRITICAL' : random(0, 100) == 0 ? 'WARNING' : 'OK'}
  brakeFluid:
    id: '00003'
    status: #{random(0, 100) == 0 ? 'CRITICAL' : random(0, 100) == 0 ? 'WARNING' : 'OK'}
    dueDate: 2019-04
  statutoryVehicleInspection:
    id: '00032'
    status: #{random(0, 100) == 0 ? 'CRITICAL' : random(0, 100) == 0 ? 'WARNING' : 'OK'}
    dueDate: 2019-04
  exhaustGasInspection:
    id: '00033'
  vehicleCheck:
    id: '00100'
    status: #{random(0, 100) == 0 ? 'CRITICAL' : random(0, 100) == 0 ? 'WARNING' : 'OK'}
    dueDate: 2019-09
    remainingMileage: 47000
  engineOil:
    id: '00001'
    status: #{random(0, 100) == 0 ? 'CRITICAL' : random(0, 100) == 0 ? 'WARNING' : 'OK'}
    dueDate: 2019-09
    remainingMileage: 11000
lastDataTransfer: '2018-10-03T04:49:43.810334Z'
dataQuality: UP_TO_DATE
usersWithReadPermissions:
  - aaaaaaaa-0000-cccc-0000-000000000099
