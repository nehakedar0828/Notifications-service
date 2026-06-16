# Notifications Service

A backend notification management service built using Spring Boot and PostgreSQL. The application provides REST APIs for creating, storing, retrieving, and managing notifications.

## Features

- Create notifications
- Retrieve all notifications
- Fetch notification by ID
- Update notification status
- Delete notifications
- PostgreSQL database integration
- Layered architecture (Controller, Service, Repository)
- RESTful API design
- Exception handling and validation

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

## Project Structure

src
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
└── config

## API Endpoints

| Method | Endpoint | Description |
|----------|-------------|-------------|
| POST | /api/notifications | Create notification |
| GET | /api/notifications | Get all notifications |
| GET | /api/notifications/{id} | Get notification by ID |
| PUT | /api/notifications/{id} | Update notification |
| DELETE | /api/notifications/{id} | Delete notification |

## Database Configuration

Update the following properties in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/notifications_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Run Locally

```bash
git clone <repository-url>
cd notifications-service

mvn clean install
mvn spring-boot:run
```

## Future Enhancements

- Email notifications
- SMS notifications
- Kafka integration
- RabbitMQ integration
- Notification scheduling
- Authentication & Authorization

## Author

Neha Kedar
