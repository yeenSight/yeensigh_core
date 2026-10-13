# yeensigh_core
core of the futur backend


## Architecture

Spring Boot 3 / Java 17, MongoDB, Kafka, packaged with Docker. Hexagonal + DDD layout:

- `domain` – model and ports (no framework dependencies)
- `application` – use cases (`EventService`), tested with in-memory fakes (TDD)
- `infrastructure` – adapters: REST, MongoDB, Kafka

Run: `docker compose up --build`; test: `mvn test`.

## Domain

An `Event` has a title, description, start/end date-time (UTC instants, end after start) and a `Location`, which is either `Physical` (address) or `Online` (http(s) URL). API: `POST /api/events`, `GET /api/events/{id}`.
