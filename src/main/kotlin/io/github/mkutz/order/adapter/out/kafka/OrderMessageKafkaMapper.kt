package io.github.mkutz.order.adapter.out.kafka

import io.github.mkutz.order.application.order.Order
import java.time.Instant

object OrderMessageKafkaMapper {

  fun toOrderCreatedMessage(order: Order): OrderCreatedMessage {
    return OrderCreatedMessage(
      orderId = order.id.value.toString(),
      items =
        order.items.map { item ->
          OrderCreatedMessage.Item(
            articleId = item.articleId.value.toString(),
            articleName = item.articleName,
            quantity = item.quantity,
            unitPrice = item.unitPrice.amount,
          )
        },
      totalAmount = order.totalPrice.amount,
      currency = order.totalPrice.currency.currencyCode,
      timestamp = Instant.now().toEpochMilli(),
    )
  }

  fun toOrderCancelledMessage(order: Order): OrderCancelledMessage {
    return OrderCancelledMessage(
      orderId = order.id.value.toString(),
      timestamp = Instant.now().toEpochMilli(),
    )
  }

  fun toPaymentConfirmedMessage(order: Order): PaymentConfirmedMessage {
    return PaymentConfirmedMessage(
      orderId = order.id.value.toString(),
      totalAmount = order.totalPrice.amount,
      currency = order.totalPrice.currency.currencyCode,
      timestamp = Instant.now().toEpochMilli(),
    )
  }
}
