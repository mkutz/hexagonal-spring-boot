package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.order.Order

class ToSaveOrdersStub : ToSaveOrders {
  private val savedOrders = mutableListOf<Order>()

  override fun saveOrder(order: Order): Order {
    savedOrders.add(order)
    return order
  }

  fun getSavedOrders(): List<Order> = savedOrders.toList()

  fun getLastSavedOrder(): Order? = savedOrders.lastOrNull()

  fun clear() {
    savedOrders.clear()
  }
}
