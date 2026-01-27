package io.github.mkutz.order.application.order

import io.github.mkutz.order.application.article.anArticle
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class OrderServiceTest {

  private val orderService = OrderService()

  @Test
  fun `createOrder creates order with correct items`() {
    val article1 = anArticle { name = "Article 1" }
    val article2 = anArticle { name = "Article 2" }

    val order = orderService.createOrder(listOf(article1 to 2, article2 to 1))

    assertThat(order.status).isEqualTo(Order.Status.CREATED)
    assertThat(order.items).hasSize(2)
    assertThat(order.items[0].articleId).isEqualTo(article1.id)
    assertThat(order.items[0].quantity).isEqualTo(2)
    assertThat(order.items[1].articleId).isEqualTo(article2.id)
    assertThat(order.items[1].quantity).isEqualTo(1)
  }

  @Test
  fun `createOrder fails with empty article list`() {
    assertThatThrownBy { orderService.createOrder(emptyList()) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessageContaining("at least one article")
  }

  @Test
  fun `createOrder fails with zero quantity`() {
    val article = anArticle()

    assertThatThrownBy { orderService.createOrder(listOf(article to 0)) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessageContaining("positive")
  }
}
