# SaaSGuard Spring Boot REST API

## Project Structure
```
src/
├── main/
│   ├── java/com/example/saasguard/
│   │   ├── SaaSGuardApplication.java       (Main entry point)
│   │   ├── controller/
│   │   │   └── SubscriptionController.java  (REST endpoints)
│   │   ├── manager/
│   │   │   └── SubscriptionManager.java     (Service layer)
│   │   ├── model/
│   │   │   └── Subscription.java            (Data model)
│   │   └── repository/
│   │       ├── SubscriptionRepository.java  (Interface)
│   │       ├── JdbcSubscriptionRepository.java (JDBC implementation)
│   │       └── DatabaseConnection.java      (Connection manager)
│   └── resources/
│       └── application.properties           (Configuration)
└── pom.xml                                  (Maven dependencies)
```

## REST API Endpoints

### 1. Get All Subscriptions
**GET** `/api/subscriptions`
- Returns all subscriptions
- Response: List of Subscription objects

### 2. Get Subscription By ID
**GET** `/api/subscriptions/{id}`
- Returns a specific subscription by ID
- Response: Subscription object or 404

### 3. Create New Subscription
**POST** `/api/subscriptions`
```json
{
  "id": 1,
  "name": "Premium Plan",
  "monthlyCost": 99.99
}
```
- Response: 201 Created with confirmation message

### 4. Update Subscription
**PUT** `/api/subscriptions/{id}`
```json
{
  "name": "Premium Plus",
  "monthlyCost": 149.99
}
```
- Response: 200 OK with confirmation message

### 5. Delete Subscription
**DELETE** `/api/subscriptions/{id}`
- Response: 200 OK with confirmation message

### 6. Get Total Monthly Cost
**GET** `/api/subscriptions/total/cost`
- Returns total monthly and yearly costs
- Response:
```json
{
  "totalMonthlyCost": 599.99,
  "totalYearlyCost": 7199.88
}
```

## Configuration
- Java Version: 17
- Spring Boot Version: 3.2.0
- Framework: Spring Boot REST API
- Database: MySQL
- ORM: JDBC (No JPA)
- Default Port: 8080
- Base Path: /api

## Running the Application
```bash
mvn clean install
mvn spring-boot:run
```

## Database Configuration
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/saasguard
spring.datasource.username=root
spring.datasource.password=l1m11y2k4
```

## Dependencies
- Spring Boot Web Starter
- Spring Boot JDBC Starter
- MySQL Connector Java 9.6.0

