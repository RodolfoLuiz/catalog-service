# Catalog Service

This microservice is responsible for managing the **Catalog** and **Genre** aggregates within the video processing architecture. It is developed using **Java**, follows **Domain-Driven Design (DDD)** principles, and was built using **Test-Driven Development (TDD)**. Integration tests are implemented using **Testcontainers**.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
  - [Running the Service](#running-the-service)
- [Domain Model](#domain-model)
- [Testing](#testing)
- [API Endpoints](#api-endpoints)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Overview

The **Catalog Service** is responsible for managing video-related metadata, including categories and genres. It ensures consistency and separation of concerns by following **DDD** principles and using aggregates for data modeling.

### Technologies:
- **Java (Spring Boot)** for service implementation
- **Testcontainers** for integration testing
- **PostgreSQL** as the primary database
- **Kafka** for event-driven communication
- **Docker** for containerized deployment

## Architecture

The service is structured around the **DDD** model and includes the following key components:

1. **Aggregates:**
   - **Catalog**: Represents collections of videos grouped by criteria.
   - **Genre**: Represents video categories and associations.
2. **Application Layer:** Handles use cases and business logic.
3. **Infrastructure Layer:** Manages database persistence and event handling.
4. **API Layer:** Exposes endpoints for catalog and genre management.

## Getting Started

### Prerequisites

Ensure you have the following installed:
- **Java 17+**
- **Docker & Docker Compose**
- **PostgreSQL**
- **Kafka** (optional for event-driven features)

You will also need the following environment variables configured:
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/catalog_db
SPRING_DATASOURCE_USERNAME=your_user
SPRING_DATASOURCE_PASSWORD=your_password
KAFKA_BROKER=localhost:9092
```

### Setup

1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-org/catalog-service.git
   cd catalog-service
   ```
2. **Start dependencies using Docker Compose:**
   ```sh
   docker-compose up -d
   ```
3. **Run the service:**
   ```sh
   ./mvnw spring-boot:run
   ```

## Domain Model

The service follows **DDD** and organizes domain logic within aggregates:

- **Catalog**: Represents a collection of categorized videos.
- **Genre**: Defines video categories and their relationships.
- **Entities**: Core business objects with unique identities.
- **Value Objects**: Immutable and model-specific properties.
- **Repositories**: Abstractions for data persistence.

## Testing

The service is developed using **TDD**, and tests include:
- **Unit Tests** (JUnit + Mockito)
- **Integration Tests** (Spring Boot Test + Testcontainers)

Run tests using:
```sh
./mvnw test
```

## API Endpoints

### Create a Genre
```http
POST /api/genres
```
**Request Body:**
```json
{
  "name": "Action",
  "description": "Action-packed movies"
}
```

### Create a Catalog Entry
```http
POST /api/catalogs
```
**Request Body:**
```json
{
  "name": "Top Movies",
  "genres": ["Action", "Drama"]
}
```

### Get Catalog by ID
```http
GET /api/catalogs/{id}
```

## Deployment

1. **Build the Docker image:**
   ```sh
   ./mvnw package -DskipTests
   docker build -t catalog-service .
   ```
2. **Run the container:**
   ```sh
   docker run -p 8080:8080 catalog-service
   ```

## Contributing

1. Fork the repository.
2. Create a new branch (`feature/my-feature`).
3. Commit your changes.
4. Push to your branch and create a Pull Request.

## License

This project is licensed under the MIT License.

