package io.github.mkutz.order.adapter.`in`.kafka

import io.github.mkutz.order.adapter.KafkaContainerConfiguration
import io.github.mkutz.order.adapter.PostgresContainerConfiguration
import io.github.mkutz.order.adapter.out.persistence.OrderPersistenceAdapter
import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.order.anOrder
import java.time.Duration
import java.util.concurrent.TimeUnit
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("adapter-test")
@Import(PostgresContainerConfiguration::class, KafkaContainerConfiguration::class)
class PaymentMessageConsumerTest
@Autowired
constructor(
  private val kafkaTemplate: KafkaTemplate<String, Any>,
  private val orderPersistenceAdapter: OrderPersistenceAdapter,
  @param:Value("\${app.kafka.topics.payment-confirmed}") private val paymentConfirmedTopic: String,
) {

  @Test
  fun `onPaymentConfirmed updates order status`() {
    val order = anOrder { status = Order.Status.CREATED }
    orderPersistenceAdapter.saveOrder(order)

    val message =
      PaymentConfirmedMessage(
        orderId = order.id.value.toString(),
        paymentId = "payment-123",
        timestamp = System.currentTimeMillis(),
      )

    kafkaTemplate
      .send(paymentConfirmedTopic, order.id.value.toString(), message)
      .get(10, TimeUnit.SECONDS)

    await().atMost(Duration.ofSeconds(30)).pollInterval(Duration.ofMillis(500)).untilAsserted {
      val updatedOrder = orderPersistenceAdapter.loadOrder(order.id)
      assertThat(updatedOrder?.status).isEqualTo(Order.Status.PAYMENT_CONFIRMED)
    }
  }
}
