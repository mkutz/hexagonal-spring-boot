package io.github.mkutz.order.adapter.out.kafka

import java.math.BigDecimal

data class PaymentConfirmedMessage(
  val orderId: String,
  val totalAmount: BigDecimal,
  val currency: String,
  val timestamp: Long,
)
