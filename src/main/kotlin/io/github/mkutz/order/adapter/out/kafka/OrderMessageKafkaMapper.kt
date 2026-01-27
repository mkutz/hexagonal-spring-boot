package io.github.mkutz.order.adapter.out.kafka

import io.github.mkutz.order.application.order.Order
import java.time.Instant
import org.springframework.stereotype.Component

@Component
class OrderMessageKafkaMapper {

  fun toOrderCreatedMessage(order: Order): OrderCreatedKafkaMessage {
    return OrderCreatedKafkaMessage(
      orderId = order.id.value.toString(),
      items =
        order.items.map { item ->
          OrderItemKafkaMessage(
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

  fun toOrderCancelledMessage(order: Order): OrderCancelledKafkaMessage {
    return OrderCancelledKafkaMessage(
      orderId = order.id.value.toString(),
      timestamp = Instant.now().toEpochMilli(),
    )
  }

  fun toPaymentConfirmedMessage(order: Order): PaymentConfirmedKafkaMessage {
    return PaymentConfirmedKafkaMessage(
      orderId = order.id.value.toString(),
      totalAmount = order.totalPrice.amount,
      currency = order.totalPrice.currency.currencyCode,
      timestamp = Instant.now().toEpochMilli(),
    )
  }
}
