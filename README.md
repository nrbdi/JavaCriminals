Class Definitions:
1. Vehicle
Represents a vehicle in the auto dealership system; contains fields like id, brand, type, model, price, release year and status(available and etc.);
provides getters and setters for accessing and modifying vehicle properties.
2. PostgresDB
Manages database connectivity between the Java application and the PostgreSQL database; implements IDB interface;
provides a method getConnection() to establish and return a database connection.
3. User
Represents a user in the system (either a customer or an admin); contains fields such as id, name, email, phone number, password, and role;
provides getters and setters for accessing and modifying user properties.
4. Characteristics
Represents the detailed specifications of a vehicle; contains fields like id, vehicleId, enginePower, fuelType, transmission, color, and mileage;
provides a constructor for initializing characteristics; includes getters and setters for accessing and modifying properties.

Repository Interfaces and Implementations:
1. IVehicleRepository
Interface defining operations for handling vehicle-related database operations.
Methods:
getAllVehicles(): Retrieves all vehicles from the database.
getVehiclesByType(String type): Retrieves vehicles filtered by type.
getVehiclesByBrand(String brand): Retrieves vehicles filtered by brand.
getVehicleById(int id): Retrieves a specific vehicle by its ID.
updateVehicleStatus(int id, String status): Updates the status of a vehicle.
2. IUserRepository
Interface defining operations for handling user-related database operations.
Methods:
createUser(User user): Creates a new user in the database.
updateUser(User user): Updates an existing user's details.
getUserById(int id): Retrieves a user by their ID.
getAllUsers(): Retrieves all users.
3. ICharacteristicsRepository
Interface defining methods for handling characteristics-related database operations.
Methods:
getCharacteristicsByVehicleId(int vehicleId):retrieves characteristics for a specific vehicle by its ID.
4. VehicleRepository
Implements IVehicleRepository.
Connects to PostgresDB and executes SQL queries related to vehicles.
5. UserRepository
Implements IUserRepository.
Connects to PostgresDB and executes SQL queries related to users. 
6. CharacteristicsRepository
Implements ICharacteristicsRepository.
Connects to the database and executes SQL queries related to vehicle characteristics.


Controller Interfaces and Implementations:
1. IVehicleController
Interface defining methods for handling vehicle-related operations from the user interface.
Methods:
getAllVehicles()
getVehiclesByType(String type)
getVehiclesByBrand(String brand)
getVehicleById(int id)
updateVehicleStatus(int id, String status)
2. IUserController
Interface defining methods for handling user-related operations.
Methods:
createUser(User user)
updateUser(User user)
getUserById(int id)
loginUser(String email, String password)
getAllUsers()
3. VehicleController
Implements IVehicleController.
Uses IVehicleRepository to interact with the database and process vehicle-related requests.
4. UserController
Implements IUserController.
Uses IUserRepository to interact with the database and process user-related requests.
5. ICharacteristicsController
Interface defining methods for handling characteristics-related operations from the user interface.
Methods:
String getCharacteristicsByVehicleId(int vehicleId): Retrieves characteristics details for a given vehicle ID. 
6. CharacteristicsController
Implements ICharacteristicsController. Uses ICharacteristicsRepository to interact with the database and process characteristics-related requests.
Methods:
String getCharacteristicsByVehicleId(int vehicleId): Retrieves and formats characteristics for display.

Application and Main Class:
1. MyApplication
Manages the user interface flow; provides cases for:
*Logging in as a user or admin.
*Viewing the catalog of vehicles.
*Filtering vehicles by brand or type.
*Navigating back to the main menu.
2. Main
Initializes database connection (PostgresDB);Instantiates repository objects;Instantiates controller objects;
Passes controllers to MyApplication and starts the application,Closes the database connection at the end.
