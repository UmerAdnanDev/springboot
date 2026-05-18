# 2-RestApi-h2-DB - Simple Product REST API

A beginner-friendly REST API demonstrating basic CRUD operations with Spring Boot and H2 database. This is the first API project after learning Spring Boot basics.

## Project Info

| Attribute | Value |
|-----------|-------|
| **Initialized with** | Spring Initializr (https://start.spring.io) |
| **Java Version** | 21 |
| **Build Tool** | Maven |
| **Spring Boot Version** | 4.0.6 |
| **Database** | H2 (In-Memory) |
| **Port** | 8081 |
| **Packaging** | JAR |

## Dependencies Used

| Dependency | Purpose |
|------------|---------|
| **Spring Web** | Build REST APIs, handle HTTP requests/responses |
| **Spring Data JPA** | Database operations, ORM, automatic SQL generation |
| **H2 Database** | In-memory database, no setup required for learning |
| **Spring Boot DevTools** | Auto-restart server on code changes |
| **Spring Boot Test** | Unit and integration testing |

## Topics Covered

### 1. REST API CRUD Operations

| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| **Create** | POST | `/api/products` |
| **Read (All)** | GET | `/api/products` |
| **Read (Single)** | GET | `/api/products/{id}` |
| **Update** | PUT | `/api/products/{id}` |
| **Delete** | DELETE | `/api/products/{id}` |

### 2. Spring Boot Annotations Used

| Annotation | Purpose | Used On |
|------------|---------|---------|
| `@SpringBootApplication` | Main entry point | Application class |
| `@RestController` | Marks class as REST controller | Controller |
| `@RequestMapping` | Base URL mapping | Controller class |
| `@GetMapping` | Handle GET requests | Controller methods |
| `@PostMapping` | Handle POST requests | Controller methods |
| `@PutMapping` | Handle PUT requests | Controller methods |
| `@DeleteMapping` | Handle DELETE requests | Controller methods |
| `@PathVariable` | Extract data from URL | Method parameters |
| `@RequestBody` | Convert JSON to Java object | Method parameters |
| `@Autowired` | Dependency injection | Repository field |

### 3. JPA/Hibernate Annotations

| Annotation | Purpose | Used On |
|------------|---------|---------|
| `@Entity` | Marks class as database entity | Product class |
| `@Table` | Specifies table name | Product class |
| `@Id` | Primary key | id field |
| `@GeneratedValue` | Auto-generate ID values | id field |

### 4. Concepts Demonstrated

| Concept | Description |
|---------|-------------|
| **Layered Architecture** | Controller → Repository → Database |
| **RESTful API Design** | Proper HTTP methods and status codes |
| **JSON Serialization** | Automatic conversion between Java and JSON |
| **Spring Data JPA** | Database operations without writing SQL |

