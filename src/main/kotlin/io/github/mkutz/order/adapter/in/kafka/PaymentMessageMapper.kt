package io.github.mkutz.order.adapter.`in`.kafka

import io.github.mkutz.order.application.order.Order
import java.util.UUID

object PaymentMessageMapper {

  fun toOrderId(message: PaymentConfirmedMessage): Order.Id {
    return Order.Id(UUID.fromString(message.orderId))
  }
}
