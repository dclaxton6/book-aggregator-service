
# A high-concurrency java 21 service designed to aggregate and normalize literary metadata from various external APIs

## Current Status
- **Phase 2:** Service Layer and Multi-Source Integration.
  - [ ] Initialize Service Layer Architecture
  - [ ] Implement Exhaustive Error Logic
  - [ ] Integrate OpenLibrary API
## Built with
- **Java 21(LTS)** - Utilizing Records and Virtual Threads. 


- **Maven** - For dependency management / build automation


- **Jackson** - JSON parsing


- **JUnit 5** - For unit and integration testing.

## Key Architectural Concepts
- Providing immutable data by default through Thread-Safe Data Transfer Objects (Java 21 Records), 
leveraging Compact Constructors to enforce Fail-Fast Validation and ensure system integrity


- To maintain system integrity, the service implements an anti-corruption layer (ACL). 
This ensures that the internal domain model remains decoupled from external 'contracts', 
allowing these sources to be normalized into a unified data model.

## Upcoming Features
-  Implement Project Loom (Virtual Threads) to enable concurrent multi-source aggregation

## Roadmap
- **Phase 3:** Spring Boot 3 Migration & PostgreSQL Persistence.
<!--
- [ ] Wrap the functional core into managed Spring service and component beans.
- [ ] Spin up a physical database engine and implement Spring Data repositories to transition from memory storage to permanent data tracking.
-->

- **Phase 4:** High-Concurrency implementation through **Project Loom**.
<!-- 
- [ ] Virtual Threads Integration: Configure concurrent execution pipelines to fetch data from your multiple API sources simultaneously without blocking execution threads.
-->

- **Phase 5:** Distributed messaging with **Apache Kafka**.
<!-- 
- [ ]Introduce Apache Kafka brokers to publish integration events whenever books are successfully aggregated or a data integrity failure occurs.
-->

## Previous Phases
   **Phase 1: Foundation and Single-Source Integration**
   - [x] Initialize project structure and maven wrapper
   - [x] Implement Jackson Infrastructure
   - [x] Integrate Google Books API
## How to run
1. Clone the repository.
2. Run `./mvnw clean install` (or `./mvnw.cmd clean install` on Windows).
3. Run `java -jar target/book-aggregator-0.0.1-SNAPSHOT.jar`.