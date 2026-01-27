package io.github.mkutz.order.adapter.out.kafka

import java.math.BigDecimal

data class OrderCreatedMessage(
  val orderId: String,
  val items: List<Item>,
  val totalAmount: BigDecimal,
  val currency: String,
  val timestamp: Long,
) {
  data class Item(
    val articleId: String,
    val articleName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
  )
}
