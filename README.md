# Odds-Based Game Application

This is a simple odds-based game developed using Kotlin and Spring Boot. Players can register, place bets on randomly generated numbers, and manage their wallet transactions.

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)

## Requirements

- Java 17
- Maven
- Kotlin 1.9.25
- An IDE (e.g., IntelliJ IDEA) for Kotlin development

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/odds-game.git
   cd odds-game
   ```

2. Build the project using Maven:

   ```bash
   mvn clean install
   ```

## Running the Application

1. Start the application:

   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

2. You can use tools like Postman or cURL to interact with the API.

## API Endpoints

### Player Registration

- **POST** `/api/register`

  Request Body:

  ```json
  {
    "name": "John",
    "surname": "Doe",
    "username": "john_doe"
  }
  ```

### Place a Bet

- **POST** `/api/bet`

  Request Body:

  ```json
  {
    "playerId": 1,
    "amount": 100,
    "numberBet": 5
  }
  ```

### Get Bets by Player ID

- **GET** `/api/bets/player/{playerId}`

### Get Wallet Transactions

- **GET** `/api/wallet/transactions/{playerId}`

### Get Top Players

- **GET** `/api/leaderboard?limit=10`

## Running Tests

To run the unit tests, use the following command:

```bash
mvn test
```

This will execute all unit tests and display the results in the console.

## Conclusion

This application provides a simple way to engage with an odds-based betting game. Feel free to extend the functionality and improve the features as needed.
