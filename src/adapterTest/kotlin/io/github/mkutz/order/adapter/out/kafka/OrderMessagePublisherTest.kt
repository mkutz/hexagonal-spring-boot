package io.github.mkutz.order.adapter.out.kafka

import io.github.mkutz.order.adapter.KafkaContainerConfiguration
import io.github.mkutz.order.adapter.PostgresContainerConfiguration
import io.github.mkutz.order.application.order.anOrder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("adapter-test")
@Import(PostgresContainerConfiguration::class, KafkaContainerConfiguration::class)
class OrderMessagePublisherTest
@Autowired
constructor(
  private val orderMessagePublisher: OrderMessagePublisher,
  private val kafkaTemplate: KafkaTemplate<String, Any>,
) {

  @Test
  fun `publishOrderCreated sends message to Kafka`() {
    val order = anOrder()

    orderMessagePublisher.publishOrderCreated(order)

    kafkaTemplate.flush()
  }

  @Test
  fun `publishOrderCancelled sends message to Kafka`() {
    val order = anOrder()

    orderMessagePublisher.publishOrderCancelled(order)

    kafkaTemplate.flush()
  }

  @Test
  fun `publishPaymentConfirmed sends message to Kafka`() {
    val order = anOrder()

    orderMessagePublisher.publishPaymentConfirmed(order)

    kafkaTemplate.flush()
  }
}
