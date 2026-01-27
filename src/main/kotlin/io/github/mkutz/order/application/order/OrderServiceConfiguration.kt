package io.github.mkutz.order.application.order

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderServiceConfiguration {

  @Bean fun orderService(): OrderService = OrderService()
}
