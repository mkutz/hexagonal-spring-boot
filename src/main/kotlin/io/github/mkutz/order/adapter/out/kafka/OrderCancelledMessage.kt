package io.github.mkutz.order.adapter.out.kafka

data class OrderCancelledMessage(val orderId: String, val timestamp: Long)
