package io.github.mkutz.order.adapter.out.kafka

data class OrderCancelledKafkaMessage(val orderId: String, val timestamp: Long)
