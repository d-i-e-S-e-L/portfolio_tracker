# Stock Portfolio REST API

Backend API for tracking stock market transactions, built with Java 21 and Spring Boot 3. The application implements a FIFO algorithm to calculate the average purchase price of stocks, accounting for partial position closures.

## Technologies

* Java 21
* Spring Boot 3 & Spring Web
* Spring Data JPA / Hibernate
* H2 Database (In-memory)
* JUnit 5 & Mockito
* Lombok

## Features

* Transaction Management: Record BUY and SELL transactions. Calculations use `BigDecimal` to ensure financial precision.
* FIFO Engine: Accurately calculates current holdings and average purchase price by consuming the oldest buying tranches first when selling.
* Validation & Error Handling: Global exception handling (`@ControllerAdvice`) preventing negative prices and zero-quantity transactions.

## Local Setup

1. Clone the repository:
   ```bash
   git clone [https://github.com/TwojLogin/portfolio_tracker.git](https://github.com/TwojLogin/portfolio_tracker.git)

2. Navigate to the directory:
   ```bash
   cd portfolio_tracker
   
3. Run the application:
   ```bash
   ./mvnw spring-boot:run

The server runs on http://localhost:8080.

## API Endpoints

1. Add Transaction

   * POST /api/portfolio/transactions
   * Body:
    ```JSON
    {
        "ticker": "AAPL",
        "quantity": 10,
        "price": 150.00,
        "transactionType": "BUY"
    }

2. Get All Transactions

   * GET /api/portfolio/transactions

3. Get Portfolio Summary

   * GET /api/portfolio/{ticker}/summary
   * Response:
    ```JSON
       {
           "ticker": "AAPL",
           "currentShares": 10,
           "averagePurchasePrice": 150.00
       }

## Database Access

H2 console is enabled for local debugging.
* URL: http://localhost:8080/h2-console
* JDBC URL: jdbc:h2:mem:portfoliodb
* Username: sa
* Password: password
