package io.github.mkutz.order.adapter

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.kafka.KafkaContainer

@TestConfiguration
class KafkaContainerConfiguration {

  companion object {
    @Container
    @JvmStatic
    val kafkaContainer: KafkaContainer = KafkaContainer("apache/kafka:3.8.0").apply { start() }
  }

  @Bean @ServiceConnection(name = "kafka") fun kafkaContainer(): KafkaContainer = kafkaContainer
}
