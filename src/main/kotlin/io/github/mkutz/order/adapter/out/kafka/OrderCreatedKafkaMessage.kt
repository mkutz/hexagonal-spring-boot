package io.github.mkutz.order.adapter.out.kafka

import java.math.BigDecimal

data class OrderCreatedKafkaMessage(
  val orderId: String,
  val items: List<OrderItemKafkaMessage>,
  val totalAmount: BigDecimal,
  val currency: String,
  val timestamp: Long,
)

data class OrderItemKafkaMessage(
  val articleId: String,
  val articleName: String,
  val quantity: Int,
  val unitPrice: BigDecimal,
)
