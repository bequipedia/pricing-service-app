# Pricing Service Application

## Description

This is a Spring Boot application that provides a REST endpoint to query product prices based on application date, product identifier, and brand identifier. It uses an in-memory H2 database initialized with given example data.

## Project Structure

Following an Hexagonal Architecture approach:

- **domain**: Contains the business logic (`Price` and `PriceRepository`).
- **application**: Contains the application logic (`PriceService`).
- **infrastructure**: Contains the infrastructure implementation (`PriceController`) and data configuration (`DataInit`).

## Requirements

- Java 17+
- Gradle

## Configuration and Execution

1. Clone the repository:
   ```sh
   git clone https://github.com/bequipedia/pricing-service-app.git
   cd pricing-service-app
2. Build and run the application:
   ```sh
   ./gradlew bootRun
3. The application will be available at [http://localhost:8080](http://localhost:8080).

## Endpoints

- `GET /prices`: Query prices.
    - Parameters:
        - `applicableDate` (ISO 8601 DateTime)
        - `productId` (int)
        - `brandId` (int)
### Example Request
    curl -X GET "http://localhost:8080/prices?applicableDate=2020-06-14T10:00:00&productId=35455&brandId=1"
### Example Response

{
"id": 1,
"brandId": 1,
"startDate": "2020-06-14T00:00:00",
"endDate": "2020-12-31T23:59:59",
"priceList": 1,
"productId": 35455,
"priority": 0,
"price": 35.50,
"curr": "EUR"
}

## Data Initialization

The H2 database is automatically initialized with example data upon application startup.
## Testing

1. Run all tests:
   ```sh
   ./gradlew test
   
   
## Implemented Tests

- **Unit Tests**: Verify the internal logic of components.
- **Integration Tests**: Verify the interaction between components.
- **E2E Tests**: Verify the complete application flow.
## Authors
- Rebeca Gonzalez
