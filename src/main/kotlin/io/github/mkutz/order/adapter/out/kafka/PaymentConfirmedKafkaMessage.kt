package io.github.mkutz.order.adapter.out.kafka

import java.math.BigDecimal

data class PaymentConfirmedKafkaMessage(
  val orderId: String,
  val totalAmount: BigDecimal,
  val currency: String,
  val timestamp: Long,
)
