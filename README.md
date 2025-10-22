# Syslab Employee Management (JDK 17)

Spring Boot 3 (Java 17) Employee Management System.

## Features
- Employee CRUD
- Search by name/email/department
- H2 in-memory DB (dev)
- Ready for MySQL/PostgreSQL (edit properties)
- Global exception handling
- CI on JDK 17

## Requirements
- JDK 17 only
- Maven 3.9+

## Quick start
```bash
mvn -v   # ensure Java 17
mvn spring-boot:run
```

### API
Base path: `/api/employees`

- POST `/` create
- GET `/` list (paged)
- GET `/{id}` get by id
- PUT `/{id}` update
- DELETE `/{id}` delete
- GET `/search?q=term` search

## Switch DB (optional)
Edit `src/main/resources/application.properties` for MySQL/PostgreSQL.

## License
MIT
