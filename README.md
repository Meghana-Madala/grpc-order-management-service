# gRPC Order Management Service

## Overview

This project is a gRPC-based Order Management Service built using Java 17, Spring Boot, Spring Data JPA, H2 Database, Protocol Buffers, and gRPC.

The service supports CRUD operations for orders:

* Create Order
* Get Order
* Update Order
* Delete Order

## Technologies Used

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* H2 Database
* Protocol Buffers (protobuf)
* gRPC
* Maven

## Project Structure

* Entity Layer
* Repository Layer
* Service Layer
* gRPC Service Layer
* Exception Handling
* Validation

## Build Instructions

Clone the repository and run:

```bash
mvn clean install
```

## Run the Application

```bash
mvn spring-boot:run
```

The application starts on:

* REST/H2 Console: http://localhost:8080/h2-console
* gRPC Server: localhost:9090

## H2 Database Configuration

JDBC URL:

```text
jdbc:h2:mem:ordersdb
```

Username:

```text
sa
```

Password:

```text
(blank)
```

## Executing Client Requests

The gRPC service exposes the following RPC operations:

### CreateOrder

Creates a new order.

### GetOrder

Retrieves an order by Order ID.

### UpdateOrder

Updates an existing order.

### DeleteOrder

Deletes an order by Order ID.

You can test the service using tools such as:

## Standalone Client

The project includes a menu-driven gRPC client that can be used to perform CRUD operations.

Run the server:

mvn spring-boot:run

Then execute:

OrderClient.java

Available Operations:

1. Create Order
2. Get Order
3. Update Order
4. Delete Order
5. Exit

The client communicates with the gRPC server running on localhost:9090.

Service Name:

```text
order.OrderService
```

Available RPC Methods:

```text
CreateOrder
GetOrder
UpdateOrder
DeleteOrder
```

## Author

Meghana Madala
