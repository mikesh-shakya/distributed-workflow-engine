# Distributed Workflow Engine

A scalable, fault-tolerant, and pluggable distributed workflow engine built using Spring Boot, Java, and PostgreSQL. This project is designed to execute long-running, multi-step workflows by coordinating task execution across distributed services.

## ✨ Features

- ⚙️ Modular architecture using a multi-module Maven setup
- 🧩 Pluggable task types and custom workflow definitions
- 📦 Distributed task execution with retry, failure handling, and persistence
- 🚀 REST APIs for triggering and monitoring workflows
- 🛡️ Spring Boot + Spring Data JPA for rapid development and testability

## 🧱 Modules

| Module       | Description                                               |
|--------------|-----------------------------------------------------------|
| `common`     | Shared domain models, enums, and repository interfaces    |
| `api-gateway`| Exposes APIs to define, start, and monitor workflows      |
| `executor`   | Worker service that polls tasks and executes logic        |

## 📁 Project Structure

```
workflow-engine/
├── common/         # Shared models (e.g., Task), enums, and repositories
├── api-gateway/    # REST endpoints to create workflows and enqueue tasks
├── executor/       # Background workers that execute tasks from queue
└── pom.xml         # Parent Maven POM with dependency management
```


## 🧪 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Maven
- Docker (for local deployment)
- JUnit + Mockito (for testing)

## 🚧 Planned Functionality

- [x] Task model and repository
- [x] API Gateway skeleton
- [x] Executor module
- [ ] Workflow orchestration engine
- [ ] Task polling and retry mechanisms
- [ ] Monitoring and metrics (Prometheus / Grafana)
- [ ] Admin UI (React or Thymeleaf)

## 🧑‍💻 How to Run Locally

> ⚠️ **Prerequisites**:
> - Docker Desktop
> - IntelliJ IDEA
> - Java 17
> - Maven

### 🔹 Step 1: Clone Repo

```
git clone https://github.com/your-username/distributed-workflow-engine.git
cd distributed-workflow-engine
```

### 🔹 Step 2: Start Infrastructure

```
cd infra
docker compose up -d
```

Starts the following services:

> - Kafka → localhost:9092
> - Zookeeper → localhost:2181
> - PostgreSQL → localhost:5432
> - Username: postgres
> - Password: postgres

### 🔹 Step 3: Build the project
mvn clean install -DskipTests

### 🔹 Step 4: Run Services
▶️ Run API Gateway
```
Main class: com.workflow.apigateway.Application
Port: 8080
cd api-gateway
mvn spring-boot:run
```

▶️ Run Executor (in a new terminal)
```
Main class: com.workflow.executor.Application
Port: 8086
cd ../executor
mvn spring-boot:run
```

### 🔹 Step 5: Submit Task via Postman
POST http://localhost:8080/tasks
Headers:

### 🔹 Step 6: Observe Task Execution
Watch executor logs in IntelliJ — task should be:

Processed
Marked as COMPLETED or FAILED


