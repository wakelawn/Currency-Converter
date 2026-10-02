# Currency Converter API

This project started as a Java Spring Boot project to learn how to build a REST API and connect different parts of a backend application together.

While building it, I worked with external APIs, databases, authentication, validation, exception handling, Swagger, and automated testing.

## What I Built

- Currency conversion using live exchange rates from ExchangeRate-API
- REST APIs for currency conversion and exchange-rate lookup
- Conversion history stored in an H2 database
- API-key based authentication using Spring Security
- Request validation
- Global exception handling
- Swagger/OpenAPI documentation
- Unit tests using JUnit and Mockito
- WebClient for communicating with the external API

## What I Learned

### Spring Boot

I learned how to structure a Spring Boot application and separate responsibilities into:

- Controllers
- Services
- Repositories
- Models
- DTOs
- Configuration
- Exception handling

### REST APIs

I learned how to create REST endpoints using Spring MVC and how to work with:

- `GET`
- `POST`
- `DELETE`
- Request bodies
- Path variables
- HTTP status codes
- JSON responses

### WebClient

I used Spring WebClient to communicate with ExchangeRate-API and learned how an application can consume data from an external REST API.

### Database and JPA

I used H2 with Spring Data JPA to store conversion history.

I learned how entities, repositories, and JPA work together to save and retrieve application data.

### Spring Security

I implemented API-key authentication using a custom security filter.

Requests are authenticated using the:

```text
X-API-KEY
```

header.

I also learned how Spring Security controls access to different endpoints.

### Validation and Exception Handling

I added validation to the conversion request and created custom exceptions for cases such as unsupported currencies and missing history records.

A global exception handler was used to return consistent error responses.

### Swagger / OpenAPI

I added Swagger/OpenAPI documentation so that the API endpoints could be explored and tested through a web interface.

I also configured the API-key authentication scheme in Swagger.

### Testing

I learned how to write automated tests using JUnit 5 and Mockito.

The project includes tests for the application context, currency service, history service, and DTOs.

I also learned how to mock the external API instead of making real API calls during unit tests.

## Project Structure

```text
src/main/java/com/javaproj/currencyConverter
├── config
├── controller
├── dto
├── exception
├── model
├── repository
└── service
```

## What This Project Taught Me

The main thing I learned from this project was how the different parts of a backend application work together.

A currency conversion request goes through authentication and validation, reaches the controller and service layer, communicates with an external API, performs the conversion, and stores the result in the database.

Building the project step by step also helped me understand how testing, security, persistence, and external API integration fit into a real Spring Boot application.

## Tech Stack

Java • Spring Boot • Spring Security • Spring Data JPA • WebClient • H2 • Maven • JUnit 5 • Mockito • Swagger/OpenAPI