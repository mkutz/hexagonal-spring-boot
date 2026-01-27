package io.github.mkutz.order.adapter.`in`.kafka

import io.github.mkutz.order.application.port.`in`.ToManageOrders
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentMessageConsumer(private val toManageOrders: ToManageOrders) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @KafkaListener(
    topics = ["\${app.kafka.topics.payment-confirmed}"],
    groupId = "\${app.kafka.consumer-group}",
  )
  fun onPaymentConfirmed(message: PaymentConfirmedMessage) {
    logger.info("Received payment confirmed message for order: ${message.orderId}")
    val orderId = PaymentMessageMapper.toOrderId(message)
    toManageOrders.confirmPayment(orderId)
    logger.info("Payment confirmed for order: ${message.orderId}")
  }
}
