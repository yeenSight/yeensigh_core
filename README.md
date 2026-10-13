# yeensigh_core
core of the futur backend


## Architecture

Spring Boot 3 / Java 17, MongoDB, Kafka, packaged with Docker. Hexagonal + DDD layout:

- `domain` – model and ports (no framework dependencies)
- `application` – use cases (`InsightService`), tested with in-memory fakes (TDD)
- `infrastructure` – adapters: REST, MongoDB, Kafka

Run: `docker compose up --build`; test: `mvn test`.
