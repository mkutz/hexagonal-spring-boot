# Hexagonal Order Service

A Spring Boot example project demonstrating **Hexagonal Architecture** (Ports & Adapters) and how it makes testing easier and more effective.

## Domain

The project implements an **Order Management** domain with three bounded contexts:

- **Order** — Create, cancel, and manage order lifecycle
- **Article** — Products that can be ordered (with pricing)
- **Shipment** — Tracking of order deliveries

## Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                           Adapters                                  │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────┐│
│  │  REST API    │  │ Kafka In     │  │ Kafka Out    │  │ Postgres ││
│  │  (driving)   │  │ (driving)    │  │ (driven)     │  │ (driven) ││
│  └──────┬───────┘  └──────┬───────┘  └──────▲───────┘  └────▲─────┘│
└─────────┼─────────────────┼─────────────────┼───────────────┼──────┘
          │                 │                 │               │
          ▼                 ▼                 │               │
┌─────────────────────────────────────────────┼───────────────┼──────┐
│                    Ports                    │               │      │
│  ┌─────────────────────────────┐  ┌─────────┴───────────────┴────┐ │
│  │         Driving (in)        │  │         Driven (out)         │ │
│  │  ToManageOrders             │  │  LoadOrderPort               │ │
│  │                             │  │  SaveOrderPort               │ │
│  │                             │  │  LoadArticlePort             │ │
│  │                             │  │  PublishOrderMessagePort     │ │
│  └──────────────┬──────────────┘  └──────────────▲───────────────┘ │
└─────────────────┼────────────────────────────────┼─────────────────┘
                  │                                │
                  ▼                                │
┌─────────────────────────────────────────────────────────────────────┐
│                         Application                                 │
│  ┌────────────────────────────────────────────────────────────────┐ │
│  │                   OrderApplicationService                      │ │
│  │         (implements all driving ports, uses driven ports)      │ │
│  └────────────────────────────────────────────────────────────────┘ │
│  ┌──────────────────┐ ┌──────────────────┐ ┌────────────────────┐   │
│  │      Order       │ │     Article      │ │     Shipment       │   │
│  │   (domain)       │ │    (domain)      │ │    (domain)        │   │
│  └──────────────────┘ └──────────────────┘ └────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

### Driving Adapters (Inbound)
- **REST API** — HTTP endpoints for order management
- **Kafka Consumer** — Listens for payment confirmation messages

### Driven Adapters (Outbound)
- **PostgreSQL** — Persistence via Spring Data JPA
- **Kafka Producer** — Publishes order lifecycle messages

## Project Structure

```
src/
├── main/kotlin/io/github/mkutz/order/
│   ├── Application.kt
│   ├── application/
│   │   ├── order/                    # Order domain
│   │   │   ├── Order.kt              # Contains Order.Id, Order.Item, Order.Status
│   │   │   ├── OrderService.kt
│   │   │   └── OrderServiceConfiguration.kt
│   │   ├── article/                  # Article domain
│   │   │   ├── Article.kt            # Contains Article.Id
│   │   │   └── Money.kt
│   │   ├── shipment/                 # Shipment domain
│   │   │   ├── Shipment.kt
│   │   │   ├── ShipmentId.kt
│   │   │   ├── ShipmentStatus.kt
│   │   │   └── TrackingNumber.kt
│   │   ├── port/
│   │   │   ├── in/                   # Driving ports
│   │   │   │   └── ToManageOrders.kt
│   │   │   └── out/                  # Driven ports
│   │   │       ├── LoadOrderPort.kt
│   │   │       ├── SaveOrderPort.kt
│   │   │       ├── LoadArticlePort.kt
│   │   │       └── PublishOrderMessagePort.kt
│   │   └── OrderApplicationService.kt
│   └── adapter/
│       ├── in/
│       │   ├── rest/                 # REST adapter
│       │   │   ├── OrderController.kt
│       │   │   ├── CreateOrderRequest.kt
│       │   │   ├── OrderItemRequest.kt
│       │   │   ├── OrderResponse.kt
│       │   │   └── OrderRestMapper.kt
│       │   └── kafka/                # Kafka consumer adapter
│       │       ├── PaymentMessageConsumer.kt
│       │       ├── PaymentConfirmedMessage.kt
│       │       └── PaymentMessageMapper.kt
│       └── out/
│           ├── persistence/          # JPA adapter
│           │   ├── OrderPersistenceAdapter.kt
│           │   ├── OrderJpaRepository.kt
│           │   ├── OrderJpaEntity.kt
│           │   ├── OrderItemJpaEntity.kt
│           │   ├── OrderPersistenceMapper.kt
│           │   ├── ArticlePersistenceAdapter.kt
│           │   ├── ArticleJpaRepository.kt
│           │   ├── ArticleJpaEntity.kt
│           │   └── ArticlePersistenceMapper.kt
│           └── kafka/                # Kafka producer adapter
│               ├── OrderMessageKafkaAdapter.kt
│               ├── OrderCreatedKafkaMessage.kt
│               ├── OrderCancelledKafkaMessage.kt
│               ├── PaymentConfirmedKafkaMessage.kt
│               └── OrderMessageKafkaMapper.kt
├── test/                             # Pure unit tests (no Spring)
├── testFixtures/                     # Shared builders & stubs
└── adapterTest/                      # Adapter tests with Spring context
```

## Technology Stack

| Concern | Technology |
|---------|------------|
| Language | Kotlin 2.1 |
| Framework | Spring Boot 3.4 |
| Build | Gradle (Kotlin DSL) |
| REST API | Spring Web |
| Messaging | Spring Kafka |
| Persistence | Spring Data JPA |
| Database | PostgreSQL |
| Migrations | Flyway |
| Testing | JUnit 5, AssertJ, Testcontainers |

## Naming Conventions

### Ports
Driving ports follow the pattern `ToManage<Aggregate>`:
- `ToManageOrders` — groups all order-related operations (create, get, cancel, confirm payment)

Driven ports follow the pattern `<Verb><Object>Port`:
- `LoadOrderPort`, `SaveOrderPort`, `LoadArticlePort`, `PublishOrderMessagePort`

### Domain Types
Related types are nested inside their parent class:
- `Order.Id`, `Order.Item`, `Order.Status` (instead of `OrderId`, `OrderItem`, `OrderStatus`)
- `Article.Id` (instead of `ArticleId`)

### Kafka
Kafka-related classes use "Message" (not "Event"):
- `PaymentConfirmedMessage`
- `OrderCreatedKafkaMessage`
- `PublishOrderMessagePort`

### Packages
- No categorizing packages like `dto/`, `entity/`, `repository/`
- All adapter classes live directly in their adapter package
- Domain-specific services live with their domain
- Cross-domain services live directly under `application/`

## Testing Strategy

### Test Suites

| Source Set | Purpose | Spring Context | Testcontainers |
|------------|---------|----------------|----------------|
| `test` | Pure unit tests | ❌ | ❌ |
| `testFixtures` | Shared builders & stubs | — | — |
| `adapterTest` | Adapter integration tests | ✅ | ✅ |

### No Mocking

This project intentionally avoids mocking frameworks. Instead:

1. **Stubs** implement output port interfaces for unit tests
2. **Testcontainers** provide real PostgreSQL and Kafka for adapter tests

### Test Fixtures

**Builders** create domain objects with sensible defaults:
```kotlin
val order = anOrder {
    status = OrderStatus.SHIPPED
    items = listOf(
        anOrderItem { quantity = 3 }
    )
}
```

**Stubs** implement port interfaces:
```kotlin
class LoadOrderPortStub : LoadOrderPort {
    private val orders = mutableMapOf<OrderId, Order>()

    fun givenOrder(order: Order) {
        orders[order.id] = order
    }

    override fun loadOrder(orderId: OrderId): Order? = orders[orderId]
}
```

Stubs and builders are in the same packages as the real classes (in `testFixtures`).

## Running the Project

### Prerequisites
- JDK 21
- Docker (for Testcontainers and local development)

### Run Tests
```bash
# Unit tests only
./gradlew test

# Adapter tests (requires Docker)
./gradlew adapterTest

# All tests
./gradlew check
```

### Start Dependencies
```bash
docker-compose up -d
```

### Run Application
```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/orders` | Create a new order |
| GET | `/api/orders/{id}` | Get order by ID |
| DELETE | `/api/orders/{id}` | Cancel an order |

### Create Order Request
```json
{
  "items": [
    {"articleId": "uuid", "quantity": 2}
  ]
}
```

### Order Response
```json
{
  "id": "uuid",
  "items": [
    {
      "articleId": "uuid",
      "articleName": "Product Name",
      "quantity": 2,
      "unitPrice": 19.99,
      "totalPrice": 39.98
    }
  ],
  "status": "CREATED",
  "totalAmount": 39.98,
  "currency": "EUR"
}
```

## Kafka Topics

| Topic | Direction | Description |
|-------|-----------|-------------|
| `payment.confirmed` | Consume | Payment confirmation from payment service |
| `order.created` | Produce | Order was created |
| `order.cancelled` | Produce | Order was cancelled |
| `order.payment-confirmed` | Produce | Payment was confirmed for order |

## Why Hexagonal Architecture?

### Testability
- **Domain logic** can be tested without Spring, databases, or Kafka
- **Adapters** can be tested in isolation with real infrastructure
- **Stubs** make test setup explicit and readable

### Flexibility
- Swap PostgreSQL for another database by implementing new adapters
- Add new entry points (GraphQL, gRPC) without touching domain logic
- Replace Kafka with RabbitMQ by implementing new message adapters

### Clarity
- Clear boundaries between domain and infrastructure
- Dependency direction always points inward (toward domain)
- Business rules are not polluted with framework concerns
