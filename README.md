# fx Clustered Data Warehouse 📊

![Project Status](https://img.shields.io/badge/Status-Production--Ready-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

---

## 🚀 Introduction

**fx Clustered Data Warehouse** is a modern, scalable solution for importing, validating, and persisting FX deals. Designed for performance, reliability, and data integrity, it leverages a clustered architecture and cutting-edge technologies.

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.5.0
- PostgreSQL 16
- Docker & Docker Compose
- Maven, JUnit 5, Mockito, MapStruct, Lombok

---

## ✨ Key Features

- Strict deal validation (unique ID, ISO currencies, timestamp, amount)
- Duplicate detection and rejection
- Partial save (no rollback)
- Detailed logging and audit
- Robust error handling
- Easy deployment with Docker

---

## 📦 Project Structure
```
clustereddatawarehouse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/progressoft/assignment/
│   │   │       ├── aspect/
│   │   │       ├── controller/
│   │   │       ├── domaine/
│   │   │       ├── exception/
│   │   │       ├── mapper/
│   │   │       ├── model/
│   │   │       ├── dto/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── Makefile
└── README.md
```

---

## ⚡ Quick Start

### Prerequisites
- JDK 17+
- Docker & Docker Compose
- Maven 3.8+

### Installation & Launch

1. Clone the repository:
```bash
git clone https://github.com/Zakaria-Kharroub/ProgressSoft-assignment/
cd ProgressSoft-assignment
```
2. Start the application:
```bash
make up
```

### Makefile Commands
- `make up` : Start Docker containers
- `make down` : Stop containers
- `make test` : Run tests
- `make build` : Build Docker images

---

## 📚 Main API

### Import an FX Deal

```
POST /api/deals
Content-Type: application/json
```

Sample request:
```json
{
  "id": "FX20250624A",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2025-06-24T14:30:00",
  "amount": 1000000.00
}
```

Sample response:
```json
{
  "id": "FX20250624A",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2025-06-24T14:30:00",
  "amount": 1000000.00
}
```

---

## 📖 Functional Overview

- Each deal is validated and persisted individually.
- Duplicates are rejected without affecting other imports.
- The clustered architecture ensures high availability and performance.

---

> Made with ❤️ by Zakaria Kharroub. For questions, open an issue on the repo.
