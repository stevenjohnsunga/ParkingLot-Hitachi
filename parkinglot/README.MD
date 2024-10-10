This is a simple java app I made for my technical exam for Hitachi.
Parking Lot by Steven John Sunga

1. Download the project (zipfile)
2. Open the project with intellij.
3. Use java 17 and then build and run the project, you must see "Started ParkinglotApplication" at the end.
4. Open postman and import the ParkingLot_StevenJohnSunga_Postman.json located in the same directory, there you will see all ready api's for test.
5. First, you will see a "register a parking lot" click on it and then input the name of location of parking lot, and the capacity of parking lot and in occupied spaces, put zero and then send.
6. After clicking send, you will get a response and you will get an unique id for the parking lot called "lotId", copy the unique id and paste it somewhere because you will need it later.
7. You will see a "register a vehicle", click on it and input the LicensePlate and the type of vehicle and the ownerName of the Vehicle and then click send.
   note in VehicleType, you must be precise, you can only input "MOTORCYCLE" or "CAR" or "TRUCK" if you put something else, you will get error.
8. After registering a vehicle, you can now check in the vehicle to a parking lot. To do that, click on "check in a vehicle" and then you must edit the url
   http://localhost:8080/api/vehicles/check-in/{lotId} , you must paste the lotID that you copied earlier in {lotId} and then in Body, in form-data, in Column of key, input licensePlate and put the exact licenseplate on the right side
   and then click send, you will get a response "Vehicle checked in successfully."
9. To check out a vehicle, click on "check out a vehicle" and edit the url http://localhost:8080/api/vehicles/check-out/{lotId}, you must paste the lotid in {lotId} and in Body, in form-data, under key put licenseplate and on the
   right side, put the exact license plate of a vehicle that you want to check out.
10. to view the occupancy in a parkling lot, simple paste this url http://localhost:8080/api/parking-lots/{lotId}/occupancy and edit the {lotId} according to the lodif of the parking lot you want to view occupancy, and request must be
    'GET' and then click send.
11. to view vehicles parked in a lot, http://localhost:8080/api/parking-lots/{lotId}/vehicles use this url and then paste the lotid of the parking lot in {lotId} that you want to view vehicles and request must be 'GET'
    click send and you will see all vehicles parked in a lot.
