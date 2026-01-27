package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.order.Order

class ToLoadOrdersStub : ToLoadOrders {
  private val orders = mutableMapOf<Order.Id, Order>()

  fun givenOrder(order: Order) {
    orders[order.id] = order
  }

  fun givenOrders(vararg ordersToAdd: Order) {
    ordersToAdd.forEach { orders[it.id] = it }
  }

  override fun loadOrder(orderId: Order.Id): Order? {
    return orders[orderId]
  }

  fun clear() {
    orders.clear()
  }
}
