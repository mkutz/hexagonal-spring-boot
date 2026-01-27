package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.order.Order

interface ToSaveOrders {
  fun saveOrder(order: Order): Order
}
