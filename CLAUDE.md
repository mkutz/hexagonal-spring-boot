# Hexagonal Spring Boot Order Service

## Project Overview

A Spring Boot example project demonstrating Hexagonal Architecture (Ports & Adapters) with:
- **Domain**: Order Management (Order, Article, Shipment bounded contexts)
- **Driven adapters**: PostgreSQL (JPA), Kafka producer
- **Driving adapters**: REST API, Kafka consumer
- **Stack**: Kotlin 2.1, Spring Boot 3.4, Gradle, Testcontainers

## Naming Conventions

### Ports

- **Driving ports** (inbound): `ToManage<Aggregate>` - consolidated single interface per aggregate
  - Example: `ToManageOrders` (not separate `ToCreateOrder`, `ToGetOrder`, etc.)
- **Driven ports** (outbound): `To<Verb><Object>s`
  - Examples: `ToLoadOrders`, `ToSaveOrders`, `ToLoadArticles`, `ToPublishOrderMessages`

### Domain Types

Data classes used only as properties of another class should be nested:
- `Order.Id`, `Order.Item`, `Order.Status`
- `Article.Id`
- `Shipment.Id`

### Messaging

- Use "Message" not "Event" for Kafka messages
- Examples: `OrderCreatedMessage`, `PaymentConfirmedMessage`

### Adapters

- REST controllers in `adapter.in.rest`
- Kafka consumers in `adapter.in.kafka`
- Persistence in `adapter.out.persistence`
- Kafka producers in `adapter.out.kafka`

## Code Style

### Mappers

Mappers are Kotlin `object`s (singletons), not Spring `@Component` classes:
```kotlin
object OrderRestMapper {
  fun toResponse(order: Order): OrderResponse { ... }
}
```

### Formatting

Run Spotless after making changes:
```bash
./gradlew spotlessApply
```

## Testing

### Test Source Sets

Uses jvm-test-suite Gradle plugin with:
- `test` - unit tests
- `adapterTest` - adapter integration tests
- `testFixtures` - shared test utilities

### Test Configuration

- Use `@SpringBootTest` for adapter tests (enables context caching across tests)
- Avoid `@DataJpaTest` and other sliced tests (creates separate contexts)
- Use constructor injection with `@Autowired constructor` for test classes:
  ```kotlin
  class OrderPersistenceTest @Autowired constructor(
    private val orderPersistence: OrderPersistence,
  ) { ... }
  ```

### Test Fixtures

- Builder pattern for domain objects: `anOrder { status = Order.Status.CREATED }`
- Stub implementations for ports (no mocking frameworks)

### Infrastructure

- Testcontainers for PostgreSQL and Kafka
- Use `org.testcontainers.kafka.KafkaContainer` with `apache/kafka:3.8.0` image (not deprecated `org.testcontainers.containers.KafkaContainer`)
