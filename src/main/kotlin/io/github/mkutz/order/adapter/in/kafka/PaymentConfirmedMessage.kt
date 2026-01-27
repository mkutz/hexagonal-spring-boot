package io.github.mkutz.order.adapter.`in`.kafka

data class PaymentConfirmedMessage(val orderId: String, val paymentId: String, val timestamp: Long)
