package io.github.mkutz.order.application.order

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.article.Money
import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

class OrderItemBuilder {
  var articleId: Article.Id = Article.Id(UUID.randomUUID())
  var articleName: String = "Test Article"
  var quantity: Int = 1
  var unitPrice: Money = Money(BigDecimal("19.99"), Currency.getInstance("EUR"))

  fun build() =
    Order.Item(
      articleId = articleId,
      articleName = articleName,
      quantity = quantity,
      unitPrice = unitPrice,
    )
}

fun anOrderItem(init: OrderItemBuilder.() -> Unit = {}) = OrderItemBuilder().apply(init).build()
