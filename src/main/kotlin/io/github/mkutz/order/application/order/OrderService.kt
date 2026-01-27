package io.github.mkutz.order.application.order

import io.github.mkutz.order.application.article.Article

class OrderService {

  fun createOrder(articles: List<Pair<Article, Int>>): Order {
    require(articles.isNotEmpty()) { "Must provide at least one article" }
    require(articles.all { it.second > 0 }) { "All quantities must be positive" }

    val items =
      articles.map { (article, quantity) ->
        Order.Item(
          articleId = article.id,
          articleName = article.name,
          quantity = quantity,
          unitPrice = article.price,
        )
      }

    return Order(id = Order.Id.generate(), items = items, status = Order.Status.CREATED)
  }
}
