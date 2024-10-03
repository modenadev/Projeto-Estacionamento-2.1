# Parking Management System - Team 01

## Challenge 02 - Compass UOL - SPRINGBOOT_AWS_AGO24

Repository created for Challenge 02 - Week 08, part of the **SPRING BOOT AWS** scholarship program by Compass UOL.

### Team 01 Members:
- Cássio Cintra Rosa
- Cassius dos Santos
- Fransergio Morato Filho
- Matheus Lui Zago
- Ruan Modena dos Santos
- Victor Sierra Iglesias

Following the challenge description, the parking management system was developed using Java Spring Boot and MySQL. The system covers the management of parking spots, the registration of entries and exits, and the calculation of fees, adhering to specific entry and exit rules.

Below is a description of the application components.

---

## Dependencies

Dependency management is done using **Maven**, so to run the application locally, the `pom.xml` file must include the following dependencies:

1. **spring-boot-starter-data-jpa**  
   Used to integrate Spring Data JPA, facilitating interaction with databases using the JPA (Java Persistence API) specification.

2. **spring-boot-starter-web**  
   Includes everything needed to create web applications, such as support for RESTful APIs, including Spring MVC and Tomcat as the embedded server.

3. **spring-boot-devtools**  
   Provides features for development, such as automatic reloading and support for real-time configuration changes. It is optional and used only at runtime.

4. **h2**  
   An in-memory database used for development and testing. It allows testing the application without the need for an external database.

5. **mysql-connector-j**  
   JDBC driver to connect to MySQL. Used to interact with a MySQL database at runtime.

6. **spring-boot-starter-test**  
   A set of dependencies for testing, including JUnit, AssertJ, Hamcrest, and Mockito. Used to write and run unit and integration tests.

7. **springdoc-openapi-starter-webmvc-ui**  
   Tool for generating API documentation using OpenAPI (Swagger). Allows viewing API documentation in a web interface.

8. **spring-boot-starter-validation**  
   Support for data validation in the application, integrating with Hibernate Validator, allowing you to validate entities and request parameters.

9. **modelmapper**  
   Library for object mapping, facilitating the conversion between different types of objects, especially useful for transferring data between layers.

To check the versions of each dependency used in the project, see the `pom.xml` file.

---

## Package main/java/resources

Package used to store resource files necessary for the application. Here, we have the following subfolders and files:

### db.migration

Package containing the following files, used by Docker:
- **V1_create_database.sql**: Script for creating the database if it does not exist.
- **V2_create_tables.sql**: Script for creating the tables in the database.
- **V3_inserts.sql**: Script for inserting data into the database.

### sql

Package with the SQL scripts necessary for creating the project's database and populating the parking spots table.

### docker-compose.yml

This file is a Docker Compose file that defines the configuration for a MySQL database service. Below is how to use it:

## Testing Docker for the Parking Service

### Prerequisites
- **Docker**: Ensure that Docker is installed on your machine. You can download and install Docker [here](https://www.docker.com/get-started).
- **Docker Compose**: Docker Compose is usually included with the Docker installation. You can check if it is installed by running `docker-compose --version` in the terminal.

### Step by Step to Run the Project

1. **Clone the Repository**: First, clone the project repository to your local machine:
   ```bash
   git clone <YOUR_REPOSITORY_URL>
   cd <PROJECT_FOLDER_NAME>

2. **Create the docker-compose.yml File**: Create a file named docker-compose.yml in the root of your project and add the following content:

version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-container
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: parking_db
      MYSQL_USER: user
      MYSQL_PASSWORD: user
    ports:
      - "3307:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    networks:
      - my-network

volumes:
  mysql-data:

networks:
  my-network:


3. **Start the Container**: In the terminal, within the project folder, run the following command to start the container:

docker-compose up -d

The -d parameter runs the container in the background.

4. **Check Running Containers**: To check if the MySQL container is running, use the command:

docker ps

You should see mysql-container in the list of running containers.

5. **Connect to MySQL**: To connect to the MySQL database, you can use a MySQL client (like MySQL Workbench) or the terminal. Use the following credentials:

-Host: localhost
-Port: 3307
-User: user
-Password: user
-Database: parking_db

If you are using the terminal, you can connect using the following command:

docker exec -it mysql-container mysql -u user -p

Then, enter the password (user) when prompted.

6. **Run the Database Script**: After connecting to MySQL, execute the SQL commands provided in your database creation script. You can copy and paste the script into the MySQL client or terminal.

**Example Database Creation Script**:

CREATE DATABASE parking_db;
USE parking_db;

CREATE TABLE vehicle(
    id INT NOT NULL AUTO_INCREMENT,
    plate VARCHAR(8),
    category VARCHAR(30),
    type_vehicle VARCHAR(30),
    active BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE ticket(
    id INT NOT NULL AUTO_INCREMENT,
    vehicle INT,
    entry_date DATETIME,
    gate_entry INT,
    exit_date DATETIME,
    gate_exit INT,
    price DOUBLE,
    parked BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (vehicle) REFERENCES vehicle(id)
);

CREATE TABLE parking_spot(
    id INT NOT NULL AUTO_INCREMENT,
    reserved BOOLEAN DEFAULT FALSE,
    vacancy_occupied BOOLEAN DEFAULT FALSE,
    occupied_by INT,
    active_vacancy BOOLEAN DEFAULT TRUE,
    ticket_id INT,
    parked BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (occupied_by) REFERENCES vehicle(id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

SET @rownum = 0;
INSERT INTO parking_spot (id, reserved, vacancy_occupied, active_vacancy)
SELECT @rownum := @rownum + 1 AS number,
CASE
    WHEN @rownum <= 200 THEN 1
    ELSE 0
END AS reserved,
FALSE AS vacancy_occupied,
TRUE AS active_vacancy
FROM (
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t1,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t2,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t3,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) AS t4
WHERE @rownum < 500;

**Stopping the Container**
When you finish using the service, you can stop the container with the command:

docker-compose down

With these steps, you should be able to test the parking service locally using Docker. Ensure that all services are working correctly and that you can access the database as needed.

---

### application.properties

Configuration file for the database connection, language support, and API documentation.

1. **spring.application.name**  
   Defines the application name, which can be used in logs and for identifying the application in microservices environments.

2. **LOCALE**  
   - `spring.web.locale-resolver`: Configures the locale resolver to a fixed value.  
   - `spring.web.locale`: Sets the default language of the application to Brazilian Portuguese (pt_BR).

3. **MySQL Database Connection Properties**  
   Necessary configurations to connect to the MySQL database:
   - `driverClassName`: JDBC driver class.
   - `url`: Connection URL to the database, including options like useSSL and timezone.
   - `username`: Username for the connection. To run the application, replace this field with your connection details.
   - `password`: Password for the connection. To run the application, replace this field with your connection details.

4. **JPA**  
   JPA (Java Persistence API) configurations:
   - `show-sql`: Displays generated SQL queries in the log.
   - `hibernate.format_sql`: Disables formatting of SQL queries in the log.
   - `ddl-auto`: Defines the behavior of table creation (in this case, without auto-creation).

5. **Springdoc OpenAPI & Swagger**  
   Configurations for API documentation:
   - `swagger-ui.path`: Defines the path to the Swagger interface.
   - `api-docs.path`: Defines the path to the API documents.
   - `packagesToScan`: Indicates which packages should be scanned to generate documentation, focusing on the application's controllers.

6. **MESSAGES i18n**  
   Internationalization (i18n) configurations:
   - `basename`: Defines the base name of the message file for multi-language support.
   - `encoding`: Sets the encoding of message files to UTF-8.

## Package `main/java/config`

Package used to store the following configuration classes:

### ModelMapperConfig
Class that defines a bean of type `ModelMapper`, allowing it to be used in other parts of the application through dependency injection.

### SpringDocOpenApiConfig
Class that configures API documentation to be easily accessible, providing useful information for developers who will interact with it.

## Package `main/java/controller`

Package with controller classes that manage HTTP requests and responses of the application. Here, the necessary endpoints for client-server interaction are defined. In all controllers, the following annotations are used:

- `@RestController`: Marks the class as a REST controller.
- `@RequestMapping`: Defines the route for the endpoints.
- HTTP annotations (@RequestMapping, @PostMapping, etc.).

Below are the controllers present in the project:

### ParkingSpotController

1. **getParkingSummary()**  
   Returns a summary of all available and occupied parking spots. Uses the `getParkingSummary` method from the service and responds with a `ParkingSpotSummaryDto` object.

2. **deleteParkingSpot(@PathVariable Long id)**  
   Removes a parking spot by the provided ID. Calls the `deleteById` method from the service and responds with status 204 (No Content) if the removal is successful.

3. **updateParkingSpot(@RequestBody ParkingSpotUpdateDto updateDto)**  
   Updates the capacity of a parking spot based on the data from the `ParkingSpotUpdateDto` object. Calls the `updateParkingSpot` method from the service and responds with status 204 (No Content) if the update is successful.

4. **findAllFreeParkingSpots()**  
   Returns a list of all available parking spots. Uses the `findByVacancyOccupiedFalse` method from the service and responds with a list of `ParkingSpotResponseDto` objects.

### TicketController

1. **create(TicketCreateDto objDto)**  
   POST method to create a new ticket. Receives a `TicketCreateDto` in the request body. Calls the `insert()` method from `TicketService` to insert the new ticket into the system. Returns a response with status 201 CREATED and the newly created ticket mapped to a `TicketResponseExitDto`.

2. **getByFilter(FilterTypeEnum filter, String value)**  
   GET method that returns a list of filtered tickets. The `filter` and `value` parameters are optional and allow filtering tickets based on defined criteria. Calls the `getByFilter()` method in `TicketService` and returns the corresponding list of tickets with status 200 OK.

3. **delete(Long id)**  
   DELETE method that removes a ticket with the specified ID. Calls the `delete()` method in `TicketService`. Returns a 204 No Content response after successful deletion.

4. **update(Long id)**  
   PATCH method to update a vehicle's exit, changing the ticket fields related to the exit barrier, exit time, and price. Updates are made by the `update()` method of `TicketService`. Returns a 200 OK response with the updated ticket.

### VehicleController

1. **addVehicle(@RequestBody VehicleRequestDTO vehicleRequestDTO)**  
   Adds a new vehicle using data from the `VehicleRequestDTO` object. Calls the `save` method from the service and returns a response with status 201 (Created) and the created `VehicleResponseDTO` object.

2. **getAllVehicles()**  
   Returns a list of all registered vehicles. Calls the `getAllVehicles` method from the service and responds with a list of `VehicleResponseDTO`.

3. **getVehicleByPlate(@PathVariable String plate)**  
   Searches for a specific vehicle by its license plate number. Calls the `getVehicleByPlate` method from the service, which returns a `VehicleResponseDTO` object encapsulated in an `Optional`.

4. **deleteVehicle(@PathVariable Long id)**  
   Removes a vehicle by the provided ID. Calls the `deleteVehicle` method from the service and responds with status 204 (No Content) if the deletion is successful.

5. **updateVehicle(@PathVariable Long id, @RequestBody VehicleRequestDTO vehicleRequestDTO)**  
   Updates the data of an existing vehicle by ID. Calls the `update` method from the service and responds with status 204 (No Content) after the update.

## Package `main/java/exceptions`

Package responsible for defining and managing custom exceptions that may occur during the application's execution. The following classes are included:

### ApiExceptionHandler
Class responsible for global exception handling. It contains the following methods:

1. **methodArgumentNotValidException(AccessDeniedException ex, HttpServletRequest request, BindingResult result)**  
   Captures access denied exceptions (`AccessDeniedException`). Returns a response with HTTP status 403 (FORBIDDEN) and includes an error message in the response body.

2. **methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result)**  
   Handles invalid argument exceptions in controller methods (`MethodArgumentNotValidException`). Returns HTTP status 422 (UNPROCESSABLE ENTITY) with the error message.

3. **NotFoundException(RuntimeException ex, HttpServletRequest request)**  
   Captures exceptions related to not found resources (`NotFoundException`). Returns a response with HTTP status 404 (NOT FOUND) and a corresponding error message.

4. **BadRequestException(RuntimeException ex, HttpServletRequest request)**  
   Handles invalid request exceptions (`BadRequestException`). Returns HTTP status 400 (BAD REQUEST) with the error message in the body.

### BadRequestException
Custom exception class extending `RuntimeException`, used to indicate errors for invalid requests in the application.

### ConflictException
Exception used in the `updateParkingSpot` method for cases of conflict between parked vehicles and available spots.

### EntityNotFoundException
Exception returned when an entity is not found.

### ErrorMessage
Class that contains information about errors occurring in the application, useful for standardizing error responses. Annotations `@JsonIgnore` and `@JsonInclude` are used to control attribute inclusion during JSON serialization.

- Ignored attributes (annotated with @JsonIgnore):
  - **path**: The request path (URI).
  - **method**: The HTTP method of the request (GET, POST, etc.).
  - **status**: The HTTP status code.
  - **statusText**: Descriptive text of the HTTP status.

Other attributes:
- **error**: A general description of the error that occurred.
- **errors**: A list of errors, included with `@JsonInclude`.

### ExceptionResponse
Model that stores information about an exception that occurred in the system. Used in `ApiExceptionHandler` to format and send an appropriate response to the client in case of an error.

### GateInvalidException
Exception used in ticket validations when a vehicle attempts to enter or exit through an invalid barrier.

### NotFoundException
Indicates that a resource was not found during an operation. It has a constructor that accepts an error message (`msg`), passed to the parent class constructor. This exception can be thrown when a resource, like a vehicle or parking spot, is unavailable, allowing the system to handle the situation appropriately.

### ParkingSpotNullException
Exception thrown when an object is not found.

### TicketCreateException
Exception thrown in case of errors during ticket creation.

### TicketNotFoundException
Exception thrown when a ticket object is not found.

### TypeVehicleException
Exception thrown in the ticket update method when the vehicle type is invalid.

- **Specific Errors**:
  - **errors**: A map containing specific errors, where the key is the field name and the value is the associated error message. This attribute is annotated with `@JsonInclude(JsonInclude.Include.NON_NULL)`, meaning it will be included in the JSON representation only if not null.

- **Methods**:
  - **addErrors(BindingResult result)**: A private method that fills the `errors` map with validation errors collected from `BindingResult`.

## Package `main/java/model`
Package containing the entity classes of the system. This package is subdivided into:

### dto
Package containing all Data Transfer Objects (DTOs) used to transfer data between application layers. The following classes are included:

1. **ParkingSpotCreateDto**: Represents the information needed to create a parking spot.
2. **ParkingSpotResponseDto**: Represents the response for a request related to a parking spot.
3. **ParkingSpotSummaryDto**: Provides a summary of parking spot information, including the number of individual occupied spots, total capacity of individual spots, number of occupied monthly spots, and total capacity of monthly spots.
4. **ParkingSpotUpdateDto**: DTO designed to update information about parking spots.
5. **TicketCreateDto**: Represents the information needed to create a ticket, excluding those that will be provided only upon vehicle exit.
6. **TicketResponseDto**: DTO for a vehicle's exit from the parking lot, updating ticket information.
7. **VehicleRequestDTO**: Used to encapsulate vehicle information during a request.
8. **VehicleResponseDTO**: Contains the information needed for a response regarding a vehicle request.

## DTOs Usage

As will be seen next, the DTOs are used in the controller layer.

### dto/mapper
This package is responsible for defining the classes and interfaces that convert domain model entities into DTOs. Here, the DTOs defined in the DTO package are converted into each other. The following classes are found in the mapper package:

1. **ParkingSpotMapper**: Maps the necessary conversions to transform the `ParkingSpot` entity into its DTOs, according to the scenario.
2. **TicketMapper**: Similar to the parking spot mapper, this performs the necessary conversions for the `Ticket` class.
3. **VehicleMapper**: Contains the necessary conversions for the `Vehicle` class.

### entity
Another sub-package of the model, the `entity` package defines the classes that represent the domain entities of the system. These classes are mapped to tables in the database and reflect the data structure used by the application. Here, we find the following classes and enums:

1. **Category**: An enum that defines the different types of vehicle categories in the system as follows:
   - **MONTHLY_PAYER**: Vehicles that pay a monthly fee.
   - **CASUAL**: Vehicles that use the parking lot occasionally and do not require registration.
   - **DELIVERY_TRUCK**: Delivery trucks that require prior registration.
   - **PUBLIC_SERVICE**: Public service vehicles that may have free access to the parking lot.

2. **ParkingSpot**: A class that represents a parking spot. Jakarta Persistence annotations are used here to define the behavior regarding the database. These annotations facilitate data persistence and the mapping between the application and the database.

3. **Ticket**: A class that represents the ticket. Similar to the case of parking spots, Jakarta annotations are used to indicate the properties of the table in the database.

4. **TypeVehicle**: An enum with the types of vehicles accepted in the system:
   - **PASSENGER_CAR**: Passenger cars, which can be monthly or occasional, occupy 2 spots.
   - **MOTORCYCLE**: Motorcycles, which can also be monthly or occasional, occupy 1 spot.
   - **DELIVERY_TRUCK**: Delivery trucks that require registration and occupy 4 spots.
   - **PUBLIC_SERVICE**: Public service vehicles that do not require registration and do not occupy spots.

5. **Vehicle**: A class that represents the vehicle entity.

### enums
Package containing the `FilterTypeEnum` class, used by the `Ticket`.

# main/java/repository

Package that defines the interfaces implemented to access and manipulate data persisted in the database:

## 1. ParkingSpotRepository

The `ParkingSpotRepository` interface extends `JpaRepository`, providing access to CRUD operations for the `ParkingSpot` entity. It includes custom methods for data manipulation:

- **findByVacancy_OccupiedFalse()**: Returns a list of spots that are not occupied.
- **countByVacancyOccupiedAndReserved(boolean occupied, boolean reserved)**: Counts the number of spots with a specific occupancy and reservation state (false or true), considering only active spots.
- **countByReserved(boolean reserved)**: Counts the total number of reserved spots that are active.
- **findByIdBetween(Long x, Long y)**: Returns a list of spots whose ID is between the specified values.
- **updateMonthlySpots(Long start, Long end)**: Updates the reserved and active state to true for spots within a specific ID range.
- **updateGeneralSpots(Long start, Long end)**: Updates the reserved state to false and active to true for general spots in an ID range, allowing these spots to be released.
- **deactivateActiveVacancy(Long threshold)**: Deactivates all spots whose ID exceeds the specified threshold.

## 2. TicketRepository

Similar to the parking spot interface, this extends `JpaRepository`. It includes custom methods:

- **findByPlate(String plate)**: Searches for a vehicle by its plate number.
- **findAllByParkingSpot(String parkingSpotNumber)**: Searches for all tickets related to a parking spot.

## 3. VehicleRepository

It includes:

- **findByPlate(String plate)**: Returns an `Optional<Vehicle>` with the vehicle corresponding to the provided plate, allowing for vehicle searches using their identification.
- **findById(Long id)**: Returns an `Optional<Vehicle>` based on the vehicle ID, facilitating the retrieval of a specific vehicle.

# main/java/services

Package with the service classes of the application, responsible for the business logic of the application. The service classes follow this general flow:

1. **Controller**: Calls methods from the `ParkingSpotService` as needed, for example, when receiving HTTP requests.
2. **Service**: Executes business logic, interacts with the repository to access or modify data, and transforms entities into DTOs.
3. **Repository**: Performs direct operations on the database using JPA.

In this package, we have the classes described below.

## ParkingSpotService 

Responsible for encapsulating the business logic related to parking spots, interacting with the repository and transforming entities into DTOs.

### Annotations

- **@Service**: Indicates that the class is a service component, allowing Spring to manage it as a bean. This makes it available for injection in other parts of the application, such as controllers.
- **@Transactional**: Specifies that the annotated methods should be executed within a transaction. This ensures that all operations within the method are treated as a single unit of work, allowing rollback in case of failure.

### Methods and Flow

- **findByVacancyOccupiedFalse()**:
  - Calls the `findByVacancy_OccupiedFalse()` method from `ParkingSpotRepository` to fetch all spots that are not occupied.
  - Uses `ParkingSpotMapper` to convert the list of `ParkingSpot` entities into a list of `ParkingSpotResponseDto`, which is returned to the controller.

- **deleteById(Long id)**:
  - Receives an ID and calls the `deleteById(id)` method from the repository to remove the corresponding spot.

- **updateParkingSpot(ParkingSpotUpdateDto updateDto)**:
  - Receives a DTO with information about monthly and general spot capacities.
  - Calculates the difference between the desired total capacity and the current number of spots, creating new instances of `ParkingSpot` if necessary.
  - Updates the state of monthly and general spots using repository methods, such as `updateMonthlySpots()` and `updateGeneralSpots()`, and deactivates active spots as needed.

- **getParkingSummary()**:
  - Collects information about occupied and available spots by calling repository methods, such as `countByVacancyOccupiedAndReserved()`.
  - Creates a `ParkingSpotSummaryDto` with the collected data and returns it.

## TicketService

Manages the business logic related to vehicles. Similar to the parking spot service class, this uses the `@Service` and `@Transactional` annotations.

### Methods and Flow

- **insert(TicketCreateDto dto)**:
  - Creates a new ticket based on the provided data, such as the vehicle plate and type. Searches for available spots, allocates them according to the vehicle type (motorcycle, car, or delivery truck), and saves the ticket to the database.

- **getByFilter(FilterTypeEnum filter, String value)**:
  - Searches for tickets based on different filters (ID, plate, or spot). Returns a list of tickets corresponding to the applied filter, or all tickets if no filter is specified.

- **delete(Long id)**:
  - Removes a ticket based on its ID, calling the corresponding repository.

- **update(Long id)**:
  - Updates a ticket to register the exit of a vehicle, including setting the exit gate and calculating the amount due based on the duration for casual vehicles.

- **getAvailableSpotsForBigVehicles(List<ParkingSpot> parkingSpots, Category category, int size)**:
  - Searches for and returns a list of contiguous available spots for large vehicles, such as cars and trucks. If there are not enough spots, it throws an exception.

- **getAvailableSpotsForMotorcycles(List<ParkingSpot> parkingSpots, Category category)**:
  - Searches for and returns the first available spot for motorcycles.

## VehicleService

Manages the business logic related to vehicles.

### Methods and Flow

- **save(VehicleRequestDTO vehicleRequestDTO)**:
  - Checks if a vehicle with the same plate already exists in the repository. If it does, it throws a `BadRequestException`.
  - Converts the DTO into a `Vehicle` entity using `VehicleMapper`.
  - Validates the plate format with `validatePlate()`.
  - Saves the vehicle in the repository and returns a `VehicleResponseDTO`.

- **getAllVehicles()**:
  - Obtains all vehicles from the repository and converts them into a list of `VehicleResponseDTO` using `VehicleMapper`.

- **getVehicleByPlate(String plate)**:
  - Searches for a vehicle by its plate number. If found, the vehicle is converted into a `VehicleResponseDTO` and returned. Otherwise, it throws a `NotFoundException`.

- **deleteVehicle(Long id)**:
  - Checks if a vehicle with the specified ID exists. If so, it is removed; otherwise, it throws a `NotFoundException`.

- **update(Long id, VehicleRequestDTO vehicleRequestDTO)**:
  - Calls `findVehicleById()` to check if the vehicle exists. If so, it updates the vehicle using the DTO data and saves the changes to the repository. Otherwise, it throws a `NotFoundException` or `BadRequestException`.

- **findVehicleById(Long id)**:
  - A protected method that searches for a vehicle by ID and returns a `VehicleResponseDTO`, throwing a `NotFoundException` if not found.

# main/java/controller

The controller layer is responsible for handling HTTP requests, interacting with the service layer, and returning responses. It defines the routes for each request and handles the incoming data. The controller classes include:

## ParkingSpotController

The `ParkingSpotController` class defines routes related to parking spots. It receives requests and calls the corresponding methods from `ParkingSpotService`.

### Annotations

- **@RestController**: Indicates that the class is a controller component and will return data as a JSON response.
- **@RequestMapping**: Defines the base URL for the controller. In this case, it is `/api/v1/parking-spot`.

### Methods and Flow

- **getAvailableParkingSpots()**:
  - Calls the service to fetch all available spots. If successful, it returns a response with the list of available spots.

- **updateParkingSpot()**:
  - Receives an update request containing information about monthly and general spot capacities. Calls the service to update the spots.

- **deleteParkingSpot()**:
  - Calls the service to remove a spot by its ID.

- **getParkingSummary()**:
  - Retrieves the parking summary by calling the service.

## TicketController

The `TicketController` handles ticket-related routes. It interacts with the `TicketService` to perform operations like creating and retrieving tickets.

### Annotations

- **@RestController**: Indicates that this class will respond with JSON data.
- **@RequestMapping**: Defines the base URL for the ticket controller as `/api/v1/ticket`.

### Methods and Flow

- **createTicket()**:
  - Receives a request to create a ticket and calls the corresponding service method.

- **getAllTickets()**:
  - Retrieves a list of all tickets from the service.

- **deleteTicket()**:
  - Calls the service to delete a ticket based on its ID.

- **updateTicket()**:
  - Updates a ticket to register a vehicle's exit, calling the service for the necessary logic.

- **getTickets()**:
  - Retrieves tickets based on filters using the service.

## VehicleController

The `VehicleController` is responsible for handling vehicle-related requests.

### Annotations

- **@RestController**: Indicates that this class will return data as a JSON response.
- **@RequestMapping**: Defines the base URL for vehicle operations as `/api/v1/vehicle`.

### Methods and Flow

- **createVehicle()**:
  - Receives a request to create a vehicle and calls the service method to save it.

- **getAllVehicles()**:
  - Calls the service to retrieve all vehicles.

- **deleteVehicle()**:
  - Calls the service to remove a vehicle by ID.

- **getVehicleByPlate()**:
  - Retrieves a vehicle based on its plate number, calling the corresponding service method.

- **updateVehicle()**:
  - Updates a vehicle's information by calling the service.

# Security Configuration

The `SecurityConfig` class is responsible for managing security configurations in the application, including authentication and authorization mechanisms. The configurations can be customized according to specific requirements, such as user roles, passwords, etc.

### Annotations

- **@EnableWebSecurity**: Activates Spring Security's web security support.
- **@Configuration**: Indicates that the class contains configuration information.

### Flow

The `configure` method sets up the security filter chain, defining the security rules for different endpoints. For example, public access might be allowed for certain endpoints, while others require authentication.

### Main Components

- **Password Encoder**: Configures the password encoder used for securing user passwords.
- **Authentication Manager**: Manages user authentication and roles.

# Error Handling

The `GlobalExceptionHandler` class manages exception handling for the application, allowing for a centralized approach to catch and respond to exceptions.

### Annotations

- **@ControllerAdvice**: Indicates that this class provides global exception handling capabilities.
- **@ExceptionHandler**: Maps exceptions to handler methods, allowing customized responses.

### Flow

- **handleBadRequestException()**: Handles `BadRequestException` and returns a structured response with an error message and HTTP status.
- **handleNotFoundException()**: Handles `NotFoundException`, providing a response for resources not found.
- **handleGenericException()**: Catches all unhandled exceptions and provides a fallback response.

### Response Structure

The global exception handler ensures that all error responses follow a consistent structure, making it easier for clients to handle errors effectively.

---

## src/test

Package dedicated to the application's test classes, subdivided as follows:

### br.com.compass.parking.parking_service/commons

The `br.com.compass.parking_service.commons` package contains mock data and utility objects for testing the parking service application. Key components include:

1. **ParkingSpotUpdateDto**: Data Transfer Objects (DTOs) used for updating parking spot information, including valid and invalid examples for testing.
2. **ParkingSpot**: Instances of the `ParkingSpot` entity, representing different states of parking spots (available, occupied, etc.) for testing purposes.
3. **Vehicle**: A mock Vehicle instance for simulating vehicle-related operations within tests.
4. **SPOT_LIST**: A list of mock parking spots, facilitating easier access and iteration during testing.

This package helps streamline the testing process by providing pre-defined, consistent mock data.

### br.com.compass.parking.parking_service/controller

Package dedicated to the tests of the controllers. Here we have the following classes:

1. **ParkingSpotControllerTest**: Unit test for the `ParkingSpotController`, using JUnit and Mockito. Contains tests for operations such as obtaining a summary of spots, deleting, updating, and retrieving occupied or available spots. The `setUp()` method initializes the `ObjectMapper` and simulates the `ParkingSpotService`. Each test checks the expected HTTP response status and confirms correct interactions with the service. Annotations like `@MockBean` allow the simulation of behaviors during tests.

2. **VehicleControllerTest**: Unit test for the `VehicleController`, utilizing the JUnit and Mockito framework. It contains tests for CRUD operations related to vehicles, such as adding, retrieving, updating, and deleting vehicles. The `setUp()` method initializes the necessary dependencies and objects, such as `VehicleRequestDTO` and `VehicleResponseDTO`. Each test simulates interaction with the `VehicleService`, checks the expected HTTP response status, and confirms that the service methods were called correctly. The use of annotations like `@InjectMocks` and `@Mock` facilitates dependency injection and simulated behavior during tests.

### br.com.compass.parking.parking_service/domain

1. **ParkingSpotRepositoryTest**: Unit test for the `ParkingSpotRepository`, using JUnit and Spring Data JPA. Contains tests that verify the count of occupied and reserved spots, updates of spots, and searches for available and occupied spots. The `setUpAll()` method saves test data before all tests, while the `clean()` method clears data after each test. Each test uses assertions to ensure expected behavior of repository operations, such as counting, updating, and searching for spots.

2. **ParkingSpotServiceTest**: Unit test for the `ParkingSpotService`, using JUnit and Mockito. The test suite covers operations like obtaining a summary of spots, updating spots with valid and invalid data, and deleting spots. The `getParkingSpots_ReturnSummary` method verifies the count of occupied and reserved spots, while `updateParkingSpot_WithValidBody` ensures that a new spot is added correctly. Tests for invalid inputs ensure that appropriate exceptions, such as `InvalidArgumentException` and `ConflictException`, are thrown. Spot deletion is also tested, ensuring exceptions are thrown for invalid IDs. The use of annotations like `@InjectMocks` and `@Mock` facilitates dependency injection and simulated behavior during tests.

### br.com.compass.parking.parking_service/services

Package dedicated to testing the service classes. Here we have the following classes:

1. **VehicleServiceTest**: Unit test suite for the `VehicleService`, using JUnit and Mockito. It tests various operations related to vehicle management, including saving, retrieving, updating, and deleting vehicles. The `beforeEach()` method prepares the necessary objects and initializes the vehicle repository. The tests validate expected behaviors, such as saving a vehicle with valid data, handling exceptions for invalid inputs, and ensuring that repository and mapper methods are called correctly. Exceptions like `BadRequestException` and `NotFoundException` are thrown and verified in error cases, ensuring the robustness of the service logic.

---

# Conclusion

In summary, this document provides a detailed overview of the project structure, design decisions, and the role of each component in the application. From the DTOs to controllers and security configurations, every aspect is designed to ensure a modular, maintainable, and secure application. The use of Spring framework components and best practices further enhances the quality and robustness of the project.