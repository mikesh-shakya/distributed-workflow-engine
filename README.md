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

```bash
# Clone the repo
git clone https://github.com/<your-username>/distributed-workflow-engine.git
cd distributed-workflow-engine

# Build the project
mvn clean install -DskipTests

# Run API Gateway
cd api-gateway
mvn spring-boot:run

# Run Executor (in a new terminal)
cd ../executor
mvn spring-boot:run
