## SMS Messaging Service

A Java Quarkus microservice that simulates an SMS messaging platform with asynchronous message processing.

## Project Overview

This microservice demonstrates:
- RESTful API design
- Asynchronous message processing with RabbitMQ
- Input validation and error handling
- Database persistence with PostgreSQL
- Clean, layered archiecture

## Architecture

This project follows a layered architecture pattern, which seperates concerns into distinct layers, each with a specific responsibility.

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Client    │────►│  Controller │────►│   Service   │────►│ Repository  │
│             │     │             │     │             │     │             │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                              │                    │
                                              ▼                    ▼
                                        ┌───────────┐       ┌───────────┐
                                        │ RabbitMQ  │       │ PostgreSQL│
                                        └───────────┘       └───────────┘
```
https://excalidraw.com/#room=2a1f462bf288e463efcd,UkvLz_ITVqqpJYjKDl_x8w

## How It Works

1. **Client Request**: A client sends an HTTP request to send an SMS message
2. **Controller Layer**: The `SmsController` receives the request, validates the JSON payload,deserializes the JSON payload into a DTO (Data Transfer Object) and delegates to the service layer
3. **Service Layer**: The `SmsService` performs business validations and persists the message to the database with a `PENDING` status. It then publishes the message to RabbitMQ 
4. **Message Queue**: RabbitMQ holds the message until a consumer is ready to process it. This decouples the API response from the actual SMS delivery simulation.
5. **Message Consumer**: The `SmsConsumer` listens to the queue, simulates SMS delivery (80% success rate), and updates the message status in the database.
6. **Database**: PostgreSQL stores all messages with their current status (`PENDING`, `DELIVERED`, `FAILED`)

## Layered Architecture

Layer             - Package            - Responsibility
1) Controller     - com.sms.controller - REST endpoints, HTTP handling
2) DTO            - com.sms.dto        - Request/Response data shapes
3) Service        - com.sms.service    - Business logic, validation orchestration
4) Repository     - com.sms.repository - Database operations
5) Model          - com.sms.model      - Database Entities
6) Validation     - com.sms.validation - Custom validators
7) Exception      - com.sms.exception  - Error handling

## Technology Stack

Technology                 - Version               - Purpose
1) Java                    - 21.0.9                - Programming language
2) Quarkus                 - 3.30.6                - Microservice framework
3) PostgreSQL              - 15+                   - Message persistence
4) RabbitMQ                - 3.x                   - Async message processing
5) Hibernate ORM Panache   - (managed by Quarkus)  - Simplified database access
6) Hibernate Validator     - (managed by Quarkus)  - Input validation
7) Maven                   - 3.9.12                - Build tool

## Features
- [X]Project setup with Quarkus
- [X] Send SMS messages via REST API
- [X] Validate source/destination phone numbers
- [X] Validate message content
- [X] Asynchronous message processing
- [X] Simulate delivery status (Delivered/Failed)
- [X] Search messages by ID
- [X] List messages by user phone number
- [X] Persist messages to PostgreSQL

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker & Docker compose

### Running Locally
1. **Clone the repository**
```bash
   git clone https://github.com/christos-partasidis/sms-messaging-service.git
   cd sms-messaging-service
```
2. **Start infrastructure (PostgreSQL & RabbitMQ)**
 ```bash
   docker-compose up -d
```

3. **Run the application in dev mode**
```bash
./mvnw quarkus:dev
```

4. **Access the application**
- Dev UI                   : http://localhost:8080/q/dev
- Swagger UI               : http://localhost:8080/q/swagger-ui 
- RabbitMQ Management      : http://localhost:15672 (guest/guest)

### API Endpoints
Method      - Endpoint                           - Description
1) `POST`   - `/api/sms/send`                    - Send a new SMS message
2) `GET`    - `/api/sms/{id}`                    - Get message by ID
3) `GET`    - `/api/sms/phone/{phoneNumber}`     - Get all messages for a phone number
4) `GET`    - `/api/sms/from/{sourceNumber}`     - Get all mesages sent from a number
5) `GET`    - `/api/sms/to/{destinationNumber}`  - Get messages sent to a number

### Example Usage
1. **Send a message**
```bash
curl -X POST http://localhost:8080/api/sms/send \
  -H "Content-Type: application/json" \
  -d '{
    "sourceNumber": "+306912345678",
    "destinationNumber": "+306987654321",
    "content": "Hello from SMS service!"
  }'
```

2. **Check message status:**
```bash
curl http://localhost:8080/api/sms/1
```

### Building a Native Executable
**With GraalVM installed:**
```bash
./mvnw package -Dnative
./target/sms-messaging-service-1.0.0-SNAPSHOT-runner
```

**Without GraalVM (using Docker):**
```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
./target/sms-messaging-service-1.0.0-SNAPSHOT-runner
```

## Testing
```bash
# Run all tests
./mvnw test
```

## Package and run
```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Design Decisions

### Why RabbitMQ over Kafka?

- **Task queue pattern**: SMS processing follows a simple send --> process --> respond pattern
- **Simpler Setup**     : RabbitMQ requires less infrastucture
- **Appropriate Scale** : For this case Kafka's streaming capabilities would be over-engineering

### Why Layered Architecture?

- **Seperation of concerns**: each layer has a single responsibility
- **Testability**: Layers can be tested independently
- **Maintainability**: Changes in one layer don't affect others

The layered architecture provides clear separation of concerns, making the codebase easier to test, maintain, and extend. Each layer has a single responsibility: controllers handle HTTP, services handle business logic, and repositories handle data access.

## Author
Christos Partasidis

## License
This project is for assessment purposes.

