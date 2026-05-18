## Project Structure

**1-Basics** - Introduction to Spring Boot with dependency injection examples

## Project Info

| Attribute | Value |
|-----------|-------|
| **Initialized with** | Spring Initializr (https://start.spring.io) |
| **Java Version** | 21 |
| **Build Tool** | Maven |
| **Spring Boot Version** | 4.0.6 |
| **Packaging** | JAR |

## Topics Covered

- `@SpringBootApplication` - Main annotation that combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`
- `@Component` - Marks a class as Spring-managed bean
- `@Configuration` - Indicates a class declares bean definitions
- `@Bean` - Declares a method that returns a Spring-managed bean
- `@Autowired` - Injects dependencies automatically
- **Manual vs Spring-managed objects** - Comparing `new` keyword with Spring container
- **Constructor Injection** - Injecting dependencies through constructor
- **Singleton Pattern** - Spring caches and reuses the same bean instance

## Annotations Used

| Annotation | Purpose |
|------------|---------|
| `@SpringBootApplication` | Entry point annotation for Spring Boot |
| `@Component` | Auto-detect and register bean |
| `@Configuration` | Define bean configuration class |
| `@Bean` | Manually declare a bean |
| `@Autowired` | Inject dependencies |

## How to Run

```bash
mvn spring-boot:run

