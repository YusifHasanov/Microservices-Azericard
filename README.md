# Microservices-based System for E-Commerce

This repository contains the source code for a microservices-based e-commerce system designed to facilitate user authentication, card management, payments, and product information retrieval. The system is composed of four key microservices: User, Card, Payment, and Product, each serving a specific purpose within the application.

## System Overview

### Microservices

- **User Service**: Handles user registration, login, card management, and transaction history.
- **Card Service**: Responsible for storing and managing card details.
- **Payment Service**: Manages payment transactions and history.
- **Product Service**: Stores product information and manages stock.

### Technologies

- **Spring Boot**: Framework for creating microservices.
- **Spring Security**: Provides authentication and authorization.
- **Spring Cloud Eureka Server**: Service discovery for managing microservices.
- **Spring Cloud Gateway**: API Gateway for routing and filtering requests.
- **PostgreSQL**: Database for persisting service data.
- **Spring Data JPA**: Connects application with databases.
- **RabbitMQ**: Handles data concurrency during payment processing.
- **Feign Client**: Facilitates communication between services.
- **MapStruct**: Maps between Java bean types.
- **Gradle**: Build automation tool used for managing dependencies, compiling, and packaging the application.

## Getting Started

### Prerequisites

- JDK 11 or later
- Gradle
- Docker and Docker Compose (optional for containerization)
- PostgreSQL
- RabbitMQ

### Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/YusifHasanov/Microservices

#### Build each microservice using Gradle

For each microservice, you need to navigate to its directory and execute the build command. Follow these steps for each microservice:

2. **Navigate to the microservice directory:**
   Replace `<microservice-directory>` with the actual directory name of your microservice.

   ```sh
   cd <microservice-directory>
   ./gradlew build
   ```
3. **Set up the PostgreSQL databases:** for each service according to the configurations found in  `application.properties`

4. **Run RabbitMQ instance:**

If using Docker, you can run a RabbitMQ container:

```sh
docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 5672:5672 rabbitmq:3-management
```
5. **Start each microservice. You can use Gradle to run the Spring Boot application:**

```sh
./gradlew bootRun
```

***Running with Docker Compose (Optional)***
####
If you prefer to containerize your services, use Docker Compose to orchestrate the containers. Make sure you have a docker-compose.yml file set up with all the necessary services. 

*Run the following command:*
```sh
docker-compose up -d
```


## API Reference

#### Get all items

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.


## Architecture

This system adopts a microservices architecture, facilitating scalability, resilience, and modular development. Services communicate asynchronously where necessary, using RabbitMQ for handling payment operations to ensure data consistency and prevent race conditions.

Spring Cloud Eureka Server provides dynamic service discovery, allowing services to find and communicate with each other. Spring Cloud Gateway acts as the entry point for the system, routing requests to the appropriate microservices.

### Contributing
We welcome contributions to improve the system. Please fork the repository and submit pull requests with your changes.
