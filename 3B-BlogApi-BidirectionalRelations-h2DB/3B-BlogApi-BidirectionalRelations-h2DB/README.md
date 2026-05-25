# 3B-BlogApi-Bidirectional - Blog API with Bidirectional Relationships

A complete REST API demonstrating **bidirectional relationships** (One-to-Many, Many-to-One, Many-to-Many) in Spring Boot with H2 persistent database. This project builds upon unidirectional concepts by adding two-way navigation between entities.

## Project Info

| Attribute | Value |
|-----------|-------|
| **Initialized with** | Spring Initializr (https://start.spring.io) |
| **Java Version** | 21 |
| **Build Tool** | Maven |
| **Spring Boot Version** | 4.0.6 |
| **Database** | H2 (Persistent - File based) |
| **Port** | 8083 |
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

### 1. Bidirectional Relationships (Two-Way Navigation)

| Relationship | Entities | Owner Side | Inverse Side (mappedBy) | Real-World Example |
|--------------|----------|------------|------------------------|-------------------|
| **One-to-Many** | User → Post | Post (has user_id) | User (mappedBy = "user") | User writes many posts |
| **One-to-Many** | Post → Comment | Comment (has post_id) | Post (mappedBy = "post") | Post has many comments |
| **Many-to-Many** | Post ↔ Category | Category (has @JoinTable) | Post (mappedBy = "categories") | Posts belong to categories |

### 2. New Concepts in Phase 3B (vs Phase 3A)

| Concept | Phase 3A (Unidirectional) | Phase 3B (Bidirectional) |
|---------|--------------------------|--------------------------|
| **Navigation** | One-way only | Two-way |
| **mappedBy** | Not used | Required on inverse side |
| **@JsonIgnore** | Not needed | Required to prevent infinite loops |
| **Helper methods** | Not needed | Required to maintain both sides |
| **Category queries** | Cannot query products | Can query posts |
| **Both-side maintenance** | Automatic | Must set both sides manually |

### 3. REST API Endpoints

#### User Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create | POST | `/api/users` |
| Read All | GET | `/api/users` |
| Read One | GET | `/api/users/{id}` |
| Read by Username | GET | `/api/users/username/{username}` |
| Read by Email | GET | `/api/users/email/{email}` |
| Search by Name | GET | `/api/users/search/{keyword}` |
| **User with Posts** | GET | `/api/users/{id}/with-posts`  |
| **Active Authors** | GET | `/api/users/active-authors`  |
| Update | PUT | `/api/users/{id}` |
| Delete | DELETE | `/api/users/{id}` |

#### Post Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create | POST | `/api/posts` |
| Read All | GET | `/api/posts` |
| Read One | GET | `/api/posts/{id}` |
| **Post with Details** | GET | `/api/posts/{id}/details` |
| By Category | GET | `/api/posts/category/{categoryName}` |
| **Comment Stats** | GET | `/api/posts/stats/comments`  |
| Update | PUT | `/api/posts/{id}` |
| Delete | DELETE | `/api/posts/{id}` |

#### Comment Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create | POST | `/api/comments` |
| Read All | GET | `/api/comments` |
| Read One | GET | `/api/comments/{id}` |
| **Comment with Post** | GET | `/api/comments/{id}/with-post`  |
| By Post | GET | `/api/comments/post/{postId}` |
| By Author | GET | `/api/comments/author/{authorName}` |
| **With Post Details** | GET | `/api/comments/post/{postId}/with-details`  |
| Update | PUT | `/api/comments/{id}` |
| Delete | DELETE | `/api/comments/{id}` |

#### Category Endpoints
| Operation | HTTP Method | Endpoint |
|-----------|-------------|----------|
| Create | POST | `/api/categories` |
| Read All | GET | `/api/categories` |
| Read One | GET | `/api/categories/{id}` |
| **Category with Posts** | GET | `/api/categories/{id}/with-posts`  |
| **Post Counts** | GET | `/api/categories/stats/post-counts`  |
| **Popular Categories** | GET | `/api/categories/popular/{minPosts}`  |
| By Name | GET | `/api/categories/name/{name}` |
| Delete | DELETE | `/api/categories/{id}` |

 **New in Phase 3B** (Bidirectional queries)

### 4. Spring Boot Annotations Used

| Annotation | Purpose | Used On |
|------------|---------|---------|
| `@SpringBootApplication` | Main entry point | Application class |
| `@RestController` | Marks class as REST controller | Controllers |
| `@RequestMapping` | Base URL mapping | Controller classes |
| `@GetMapping`/`@PostMapping`/etc | HTTP method mapping | Controller methods |
| `@PathVariable` | Extract data from URL | Method parameters |
| `@RequestBody` | Convert JSON to Java object | Method parameters |
| `@Autowired` | Dependency injection | Repository fields |

### 5. JPA/Hibernate Annotations for Bidirectional

| Annotation | Purpose | Used On |
|------------|---------|---------|
| `@Entity` | Marks class as database entity | All entity classes |
| `@Table` | Specifies table name | Entity classes |
| `@Id` | Primary key | id fields |
| `@GeneratedValue` | Auto-generate ID values | id fields |
| `@OneToMany` | One-to-many relationship | Parent side (with mappedBy) |
| `@ManyToOne` | Many-to-one relationship | Child side (owner) |
| `@ManyToMany` | Many-to-many relationship | Either side |
| `@JoinColumn` | Foreign key column | Owner side |
| `@JoinTable` | Join table for Many-to-Many | Owner side |
| `mappedBy` | Points to owner side field | Inverse side |
| `@JsonIgnore` | Prevents JSON infinite loops | Inverse side collections |

### 6. Concepts Demonstrated

| Concept | Description |
|---------|-------------|
| **Bidirectional Relationships** | Navigation both ways between entities |
| **mappedBy Attribute** | Defines which side OWNS the relationship |
| **Owner vs Inverse Side** | Owner has foreign key, inverse uses mappedBy |
| **Helper Methods** | Maintain both sides of relationship |
| **@JsonIgnore** | Prevent JSON infinite recursion |
| **Lazy Loading** | Related entities loaded only when accessed |
| **JOIN FETCH** | Eager load lazy associations in queries |
| **Cascade Operations** | Propagate saves/deletes to children |
| **Orphan Removal** | Delete children when removed from collection |

## Database Schema (Auto-generated)

### Tables Created by Hibernate

| Table | Description | Columns |
|-------|-------------|---------|
| `users` | Blog authors | id, username, email, full_name, registered_at |
| `post` | Blog posts | id, title, content, published_at, user_id |
| `comment` | Post comments | id, author_name, content, created_at, post_id |
| `category` | Post categories | id, name, description |
| `post_category` | Join table | post_id, category_id |

### Foreign Key Constraints (Bidirectional)

| Constraint | From | To | Type |
|------------|------|-----|------|
| `user_id` | post | users | Many-to-One |
| `post_id` | comment | post | Many-to-One |
| `post_id` | post_category | post | Many-to-Many |
| `category_id` | post_category | category | Many-to-Many |
