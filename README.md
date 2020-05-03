####Prerequisties
- Java 8, Maven

####To Build
- mvn clean package

####To Test
- mvn test

####Design overview
1. ParkingLot takes in an initial number of parking slots and creates them with increasing distance to the entry point
2. An AppContext is passed around to various commands. AppContext is extendable
3. A PriorityQueue is utilized to ensure that the slot with the minimum distance (if slots are available) is always returned
4. Command Options other than those in the spec (create_parking_lot, park, leave, status, registration_numbers_for_cars_with_colour, 
slot_numbers_for_cars_with_colour, slot_number_for_registration_number) prints "Invalid command option"
5. Should commands be issued before a lot is created, "Lot has to created before operations can be done on it" will be
printed
6. If there are no vehicles/slots containing vehicles of a certain colour, "No vehicles of such colour" will be printed
7. If more arguments are passed than required, the command will run with the required number of args

