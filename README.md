# TODO API Tests

This repository contains automated tests for a TODO manager application. The application provides basic CRUD operations
and WebSocket support for real-time updates.

## Table of Contents

- [About the Project](#about-the-project)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Setup](#setup)
- [Performance Testing](#performance-testing)
- [Project Structure](#project-structure)

---

## About the Project

This project implements automated testing for a TODO manager API. The application supports basic operations such as
creating, updating, retrieving, and deleting TODOs. It also includes WebSocket functionality for real-time updates.

The project covers:

- Functional testing of API endpoints.
- Performance testing of the `POST /todos` endpoint.
- Validation of WebSocket functionality.

## Technologies Used

- Technologies Used
- Java 17
- Maven
- JUnit 5 for testing
- RestAssured for API validation
- Java-WebSocket for WebSocket validation
- Docker for running the TODO application

---

## Features

### API Endpoints Tested:

- **`GET /todos`** - Retrieve a list of TODOs with pagination support.
- **`POST /todos`** - Create a new TODO.
- **`PUT /todos/:id`** - Update an existing TODO.
- **`DELETE /todos/:id`** - Delete a TODO with authentication.
- **`/ws`** - WebSocket for receiving real-time updates.

### Key Highlights:

- Functional tests and validation checks for data consistency.
- Parameterized tests for edge cases.
- WebSocket connection and message validation.
- Performance testing for concurrent requests.

---

## Setup

### Prerequisites

1. [Docker](https://www.docker.com/) installed on your machine.
2. [Java 17](https://openjdk.org/projects/jdk/17/) or higher installed.
3. [Maven](https://maven.apache.org/) installed.

## Performance Testing

The PostTodoPerformanceTest assesses the performance of the POST /todos endpoint under concurrent load. The test uses
multiple threads to send requests and measures:

- Average request time.
- Maximum and minimum request times.
- Total time taken for all requests.
- Key Metrics
- Threads: 10
- Requests per thread: 100
- Total Requests: 1000
- The results are printed in the console after the test run.

## Project Structure

The project is organized as follows:

```plaintext
todo-api-tests/
├── src/
│   ├── main/
│   │   ├── java/
│   │       ├── com/
│   │           ├── EntitiesNames.java          # Constants for entity names
│   │           ├── TodoAppMethods.java         # Methods for API interactions
│   │           ├── model/
│   │           │   ├── TodoTask.java           # Data model for TODO entity
│   │           ├── utils/
│   │               ├── UtilJava.java           # Utility methods
│   ├── test/
│       ├── java/
│           ├── com/
│               ├── api/
│                   ├── functional/
│                   │   ├── delete/
│                   │   │   ├── DeleteTodoTaskTest.java     # Tests for DELETE /todos/:id
│                   │   ├── get/
│                   │   │   ├── GetTodoTasksTest.java       # Tests for GET /todos
│                   │   ├── post/
│                   │   │   ├── PostTodoTaskTest.java       # Tests for POST /todos
│                   │   ├── put/
│                   │   │   ├── PutTodoTaskTest.java        # Tests for PUT /todos/:id
│                   │   ├── webSocket/
│                   │       ├── WebSocketTest.java          # Tests for WebSocket functionality
│                   ├── checks/
│                   │   ├── TestChecks.java                 # Common validation checks
│                   ├── steps/
│                   │   ├── TestSteps.java                  # Test steps abstraction
│                   ├── performance/
│                       ├── PostTodoPerformanceTest.java    # Performance tests for POST /todos
├── .gitignore                                              # Git ignore rules
├── pom.xml                                                 # Maven project file
├── README.md                                               # Project documentation


