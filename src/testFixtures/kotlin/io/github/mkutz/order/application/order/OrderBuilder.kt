package io.github.mkutz.order.application.order

import java.util.UUID

class OrderBuilder {
  var id: Order.Id = Order.Id(UUID.randomUUID())
  var items: List<Order.Item> = listOf(anOrderItem())
  var status: Order.Status = Order.Status.CREATED

  fun build() = Order(id = id, items = items, status = status)
}

fun anOrder(init: OrderBuilder.() -> Unit = {}) = OrderBuilder().apply(init).build()
