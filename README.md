
# A high-concurrency java 21 service designed to aggregate and normalize literary metadata from various external APIs

## Current Status
-  **Phase 1: Foundation and Single-Source Integration**
    - [x] Initialize project structure and maven wrapper
    - [x] Implement Jackson Infrastructure
    - [ ] Integrate Google Books API 

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
- **Phase 2:** Spring Boot 3 Migration & PostgreSQL Persistence.


- **Phase 3:** High-Concurrency implementation through **Project Loom**.


- **Phase 4:** Distributed messaging with **Apache Kafka**.

## How to run
1. Clone the repository.
2. Run `./mvnw clean install` (or `mvnw.cmd` on Windows).
3. Run `java -jar target/book-aggregator-0.0.1-SNAPSHOT.jar`.