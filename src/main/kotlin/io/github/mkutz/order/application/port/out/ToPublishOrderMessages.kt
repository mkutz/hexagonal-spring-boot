package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.order.Order

interface ToPublishOrderMessages {
  fun publishOrderCreated(order: Order)

  fun publishOrderCancelled(order: Order)

  fun publishPaymentConfirmed(order: Order)
}
