package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.adapter.KafkaContainerConfiguration
import io.github.mkutz.order.adapter.PostgresContainerConfiguration
import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.order.anOrder
import io.github.mkutz.order.application.order.anOrderItem
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("adapter-test")
@Import(PostgresContainerConfiguration::class, KafkaContainerConfiguration::class)
class OrderPersistenceAdapterTest
@Autowired
constructor(private val orderPersistenceAdapter: OrderPersistenceAdapter) {

  @Test
  fun `saveOrder persists order and returns it`() {
    val order = anOrder {
      items =
        listOf(
          anOrderItem { articleName = "Test Article 1" },
          anOrderItem { articleName = "Test Article 2" },
        )
    }

    val savedOrder = orderPersistenceAdapter.saveOrder(order)

    assertThat(savedOrder.id).isEqualTo(order.id)
    assertThat(savedOrder.status).isEqualTo(order.status)
    assertThat(savedOrder.items).hasSize(2)
  }

  @Test
  fun `loadOrder returns order when exists`() {
    val order = anOrder()
    orderPersistenceAdapter.saveOrder(order)

    val loadedOrder = orderPersistenceAdapter.loadOrder(order.id)

    assertThat(loadedOrder).isNotNull
    assertThat(loadedOrder?.id).isEqualTo(order.id)
    assertThat(loadedOrder?.status).isEqualTo(order.status)
  }

  @Test
  fun `loadOrder returns null when order does not exist`() {
    val nonExistentId = Order.Id(UUID.randomUUID())

    val loadedOrder = orderPersistenceAdapter.loadOrder(nonExistentId)

    assertThat(loadedOrder).isNull()
  }

  @Test
  fun `saveOrder updates existing order`() {
    val order = anOrder { status = Order.Status.CREATED }
    orderPersistenceAdapter.saveOrder(order)

    val updatedOrder = order.copy(status = Order.Status.PAYMENT_CONFIRMED)
    orderPersistenceAdapter.saveOrder(updatedOrder)

    val loadedOrder = orderPersistenceAdapter.loadOrder(order.id)
    assertThat(loadedOrder?.status).isEqualTo(Order.Status.PAYMENT_CONFIRMED)
  }
}
