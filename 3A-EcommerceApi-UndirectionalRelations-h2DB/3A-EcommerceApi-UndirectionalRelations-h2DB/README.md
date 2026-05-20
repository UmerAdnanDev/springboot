# 3A-EcommerceApi-Unidirectional - E-commerce API with Unidirectional Relationships

A complete REST API demonstrating unidirectional relationships (One-to-One, One-to-Many, Many-to-Many) in Spring Boot with H2 persistent database. This project builds upon basic CRUD by adding entity relationships.

## Project Info

| Attribute | Value |
|-----------|-------|
| **Initialized with** | Spring Initializr (https://start.spring.io) |
| **Java Version** | 21 |
| **Build Tool** | Maven |
| **Spring Boot Version** | 4.0.6 |
| **Database** | H2 (Persistent - File based) |
| **Port** | 8082 |
| **Packaging** | JAR |

## Dependencies Used

| Dependency | Purpose |
|------------|---------|
| **Spring Web** | Build REST APIs, handle HTTP requests/responses |
| **Spring Data JPA** | Database operations, ORM, automatic SQL generation |
| **H2 Database** | Persistent file-based database for learning |
| **Spring Boot DevTools** | Auto-restart server on code changes |
| **Spring Boot Test** | Unit and integration testing |

## Topics Covered

### 1. Unidirectional Relationships (One-Way Navigation)

| Relationship | Entities | Navigation | Real-World Example |
|--------------|----------|------------|-------------------|
| **One-to-One** | User → Profile | User knows Profile, Profile doesn't know User | Each user has one profile |
| **One-to-Many** | Cart → CartItems | Cart knows items, Items don't know Cart | Shopping cart contains items |
| **Many-to-Many** | Product → Categories | Product knows categories, Categories don't know products | Products belong to multiple categories |

### 2. REST API Endpoints

#### Category Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create | POST | `/api/categories` |
| Read All | GET | `/api/categories` |
| Read One | GET | `/api/categories/{id}` |
| Read by Name | GET | `/api/categories/name/{name}` |
| Delete | DELETE | `/api/categories/{id}` |

#### Product Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create | POST | `/api/products` |
| Read All | GET | `/api/products` |
| Read One | GET | `/api/products/{id}` |
| Filter by Price | GET | `/api/products/price?min=X&max=Y` |
| Search by Keyword | GET | `/api/products/search?keyword=X` |
| By Category | GET | `/api/products/category/{name}` |
| Uncategorized | GET | `/api/products/uncategorized` |
| Reduce Stock | PUT | `/api/products/{id}/reduce-stock?quantity=X` |
| Delete | DELETE | `/api/products/{id}` |

#### User Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create | POST | `/api/users` |
| Read All | GET | `/api/users` |
| Read One | GET | `/api/users/{id}` |
| Read by Username | GET | `/api/users/username/{username}` |
| Read by City | GET | `/api/users/city/{city}` |
| Active Cart Users | GET | `/api/users/active-cart` |
| Delete | DELETE | `/api/users/{id}` |

#### Cart Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create Cart | POST | `/api/carts` |
| Get Cart | GET | `/api/carts/{id}` |
| Get Cart by User | GET | `/api/carts/user/{userId}` |
| Add Item | POST | `/api/carts/{cartId}/items` |
| Get Total | GET | `/api/carts/{cartId}/total` |
| Abandoned Carts | GET | `/api/carts/abandoned` |
| Checkout | PUT | `/api/carts/{cartId}/checkout` |
| Delete Cart | DELETE | `/api/carts/{id}` |

### 3. Spring Boot Annotations Used

| Annotation | Purpose | Used On |
|------------|---------|---------|
| `@SpringBootApplication` | Main entry point | Application class |
| `@RestController` | Marks class as REST controller | Controllers |
| `@RequestMapping` | Base URL mapping | Controller classes |
| `@GetMapping` | Handle GET requests | Controller methods |
| `@PostMapping` | Handle POST requests | Controller methods |
| `@PutMapping` | Handle PUT requests | Controller methods |
| `@DeleteMapping` | Handle DELETE requests | Controller methods |
| `@PathVariable` | Extract data from URL | Method parameters |
| `@RequestBody` | Convert JSON to Java object | Method parameters |
| `@Autowired` | Dependency injection | Repository fields |

### 4. JPA/Hibernate Annotations

| Annotation | Purpose | Used On |
|------------|---------|---------|
| `@Entity` | Marks class as database entity | All entity classes |
| `@Table` | Specifies table name | Entity classes |
| `@Id` | Primary key | id fields |
| `@GeneratedValue` | Auto-generate ID values | id fields |
| `@OneToOne` | One-to-one relationship | User → Profile |
| `@OneToMany` | One-to-many relationship | Cart → CartItems |
| `@ManyToMany` | Many-to-many relationship | Product → Categories |
| `@JoinColumn` | Foreign key column | Owner side of relationship |
| `@JoinTable` | Join table for Many-to-Many | Product → Categories |

### 5. Repository Query Types

| Query Type | Example | When to Use |
|------------|---------|-------------|
| **Derived Query** | `findByName(String name)` | Simple queries based on property names |
| **JPQL with @Query** | `@Query("SELECT p FROM Product p WHERE p.price > :price")` | Complex queries with joins |
| **Aggregation Query** | `@Query("SELECT AVG(p.price) FROM Product p")` | Statistics and calculations |
| **Native Query** | `@Query(value = "SELECT * FROM product", nativeQuery = true)` | Database-specific features |

### 6. Concepts Demonstrated

| Concept | Description |
|---------|-------------|
| **Unidirectional Relationships** | Navigation only one way (Product → Category, not Category → Product) |
| **Cascade Operations** | Operations propagate from parent to child (CascadeType.ALL) |
| **Orphan Removal** | Child entities deleted when removed from parent collection |
| **Lazy Loading** | Related entities loaded only when accessed (fetch = FetchType.LAZY) |
| **Persistent Database** | H2 file-based storage (data survives app restart) |

