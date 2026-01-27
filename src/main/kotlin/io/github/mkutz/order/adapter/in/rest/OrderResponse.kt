package io.github.mkutz.order.adapter.`in`.rest

import java.math.BigDecimal

data class OrderResponse(
  val id: String,
  val items: List<OrderItemResponse>,
  val status: String,
  val totalAmount: BigDecimal,
  val currency: String,
)

data class OrderItemResponse(
  val articleId: String,
  val articleName: String,
  val quantity: Int,
  val unitPrice: BigDecimal,
  val totalPrice: BigDecimal,
)
