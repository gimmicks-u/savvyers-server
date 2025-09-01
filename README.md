# Savvyers Server

Korean food and nutrition data management system built with Spring Boot 3.5.5.

## Quick Start

### Prerequisites
- Java 17+
- MySQL 8.0+
- Gradle 8.14.3+ (or use included wrapper)

### Environment Setup
1. **Database Configuration**
   ```bash
   export MYSQL_URL="jdbc:mysql://localhost:3306/savvyers"
   export MYSQL_USERNAME="your_username"
   export MYSQL_PASSWORD="your_password"
   ```

2. **Alternative: .env file**
   ```bash
   MYSQL_URL=jdbc:mysql://localhost:3306/savvyers
   MYSQL_USERNAME=your_username
   MYSQL_PASSWORD=your_password
   ```

### Build & Run
```bash
# Build the project
./gradlew build

# Run locally
./gradlew bootRun

# Run tests
./gradlew test
```

### Access Points
- **Application**: http://localhost:8080
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/ → "Hello World"

---

## Project Structure

```
savvyers-server/
├── docs/                          # Project documentation
│   ├── architecture.md            # System architecture overview
│   ├── api.md                     # API endpoint documentation  
│   └── database.md                # Database schema reference
├── src/
│   ├── main/
│   │   ├── java/com/savvyers/savvyersserver/
│   │   │   ├── SavvyersServerApplication.java  # Main app + root controller
│   │   │   └── product/                        # Product domain
│   │   │       ├── ProductController.java      # REST endpoints
│   │   │       ├── ProductService.java         # Business logic
│   │   │       ├── dto/                        # Data transfer objects
│   │   │       └── entity/                     # JPA entities
│   │   │           ├── Product.java            # Nutrition data
│   │   │           ├── ProductInfo.java        # Metadata
│   │   │           └── ItemManufactureReportNumber.java
│   │   └── resources/
│   │       └── application.yml                 # Spring configuration
│   └── test/
├── build.gradle                   # Gradle build configuration
└── CLAUDE.md                      # Development guidance
```

---

## Technology Stack

### Core Framework
- **Spring Boot**: 3.5.5 (Web MVC, Data JPA, Validation)
- **Java**: 17 with Gradle toolchain
- **Build**: Gradle 8.14.3

### Database & Search
- **MySQL**: Primary data storage with `savvyers` schema
- **Elasticsearch**: Search capabilities (Spring Data Elasticsearch)
- **JPA/Hibernate**: ORM with Jakarta Persistence API

### Development Tools
- **Lombok**: Boilerplate reduction
- **SpringDoc OpenAPI**: API documentation (Swagger)
- **Spring DevTools**: Hot reloading
- **Jakarta Validation**: Input validation

---

## API Overview

### Current Endpoints
```http
GET /              # Health check → "Hello World"
GET /product       # Product list → "hello" (placeholder)
```

### Documentation
- **Interactive**: http://localhost:8080/swagger-ui.html
- **Reference**: [docs/api.md](docs/api.md)

---

## Database Schema

### Core Entities
- **Product**: Comprehensive nutritional data with hierarchical food classification
- **ProductInfo**: Manufacturing details, images, allergen information  
- **ItemManufactureReportNumber**: Links products to regulatory report numbers

### Key Features
- **Nutritional Completeness**: 20+ nutritional fields (vitamins, minerals, macronutrients)
- **Classification Hierarchy**: 7-level food categorization system
- **Korean Food Focus**: UTF-8 support for Korean language data
- **Regulatory Compliance**: Manufacturing report number tracking

**Detailed Schema**: [docs/database.md](docs/database.md)

---

## Development Commands

### Build Operations
```bash
./gradlew clean          # Clean build artifacts
./gradlew build          # Full project build
./gradlew bootJar        # Create executable JAR
./gradlew check          # Run all quality checks
```

### Development Server
```bash
./gradlew bootRun        # Start development server
./gradlew bootRun --args='--spring.profiles.active=dev'  # With profile
```

### Testing
```bash
./gradlew test                                          # All tests
./gradlew test --tests "*.SavvyersServerApplicationTests"  # Specific test
./gradlew test --info                                   # Verbose output
```

---

## Configuration

### Database Setup
1. **Create MySQL Schema**
   ```sql
   CREATE SCHEMA savvyers CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **Enable JPA (Optional)**
   ```yaml
   # Uncomment in application.yml
   jpa:
     hibernate:
       ddl-auto: update
     show-sql: true
   ```

### Environment Variables
| Variable | Required | Description |
|----------|----------|-------------|
| `MYSQL_URL` | ✅ | MySQL connection URL |
| `MYSQL_USERNAME` | ✅ | Database username |
| `MYSQL_PASSWORD` | ✅ | Database password |

---

## Architecture

**Pattern**: Layered architecture with domain-driven design
**Documentation**: [docs/architecture.md](docs/architecture.md)

```
┌─────────────────┐
│   Controllers   │  REST endpoints
├─────────────────┤
│    Services     │  Business logic  
├─────────────────┤
│   Repositories  │  Data access (planned)
├─────────────────┤
│    Entities     │  JPA domain models
└─────────────────┘
```

---

## Contributing

### Code Standards
- **Java 17**: Modern language features
- **Lombok**: Minimize boilerplate with `@Getter`, `@Setter`, `@RequiredArgsConstructor`
- **Validation**: Jakarta Bean Validation for input constraints
- **Testing**: JUnit 5 with Spring Boot Test

### Development Flow
1. Feature branch from `main`
2. Implement with tests
3. Run `./gradlew check` 
4. Submit pull request

---

## Deployment

### JAR Deployment
```bash
./gradlew bootJar
java -jar build/libs/savvyers-server-0.0.1-SNAPSHOT.jar
```

### Environment Configuration
- Production: Set environment variables
- Staging: Spring profiles for environment-specific config
- Development: Local MySQL with default settings

---

## Resources

### Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/3.5.5/reference/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/)
- [Jakarta Validation](https://jakarta.ee/specifications/bean-validation/)

### Related Links
- [Project Documentation Index](docs/index.md)
- [API Reference](docs/api.md)
- [Database Schema](docs/database.md)
- [Architecture Overview](docs/architecture.md)