package io.github.mkutz.order.adapter

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@TestConfiguration
class PostgresContainerConfiguration {

  companion object {
    @Container
    @JvmStatic
    val postgresContainer: PostgreSQLContainer<*> =
      PostgreSQLContainer("postgres:16-alpine").apply { start() }
  }

  @Bean
  @ServiceConnection(name = "postgres")
  fun postgresContainer(): PostgreSQLContainer<*> = postgresContainer
}
