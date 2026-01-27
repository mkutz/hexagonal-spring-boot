package io.github.mkutz.order.adapter.out.kafka

import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.port.out.ToPublishOrderMessages
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderMessagePublisher(
  private val kafkaTemplate: KafkaTemplate<String, Any>,
  @param:Value("\${app.kafka.topics.order-created}") private val orderCreatedTopic: String,
  @param:Value("\${app.kafka.topics.order-cancelled}") private val orderCancelledTopic: String,
  @param:Value("\${app.kafka.topics.payment-confirmed-out}")
  private val paymentConfirmedOutTopic: String,
) : ToPublishOrderMessages {

  private val logger = LoggerFactory.getLogger(javaClass)

  override fun publishOrderCreated(order: Order) {
    val message = OrderMessageKafkaMapper.toOrderCreatedMessage(order)
    logger.info("Publishing order created message for order: ${order.id}")
    kafkaTemplate.send(orderCreatedTopic, order.id.value.toString(), message)
  }

  override fun publishOrderCancelled(order: Order) {
    val message = OrderMessageKafkaMapper.toOrderCancelledMessage(order)
    logger.info("Publishing order cancelled message for order: ${order.id}")
    kafkaTemplate.send(orderCancelledTopic, order.id.value.toString(), message)
  }

  override fun publishPaymentConfirmed(order: Order) {
    val message = OrderMessageKafkaMapper.toPaymentConfirmedMessage(order)
    logger.info("Publishing payment confirmed message for order: ${order.id}")
    kafkaTemplate.send(paymentConfirmedOutTopic, order.id.value.toString(), message)
  }
}
