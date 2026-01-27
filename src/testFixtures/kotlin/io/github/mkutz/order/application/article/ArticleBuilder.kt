package io.github.mkutz.order.application.article

import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

class ArticleBuilder {
  var id: Article.Id = Article.Id(UUID.randomUUID())
  var name: String = "Test Article"
  var price: Money = Money(BigDecimal("19.99"), Currency.getInstance("EUR"))

  fun build() = Article(id = id, name = name, price = price)
}

fun anArticle(init: ArticleBuilder.() -> Unit = {}) = ArticleBuilder().apply(init).build()
