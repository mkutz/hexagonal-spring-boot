package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.order.Order

interface ToLoadOrders {
  fun loadOrder(orderId: Order.Id): Order?
}
