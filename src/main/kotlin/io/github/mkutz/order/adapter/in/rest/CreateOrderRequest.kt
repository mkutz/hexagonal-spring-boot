package io.github.mkutz.order.adapter.`in`.rest

data class CreateOrderRequest(val items: List<OrderItemRequest>)

data class OrderItemRequest(val articleId: String, val quantity: Int)
