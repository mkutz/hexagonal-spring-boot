package io.github.mkutz.order.application.port.`in`

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.order.Order

interface ToManageOrders {
  fun createOrder(items: List<OrderItemRequest>): Order

  fun getOrder(orderId: Order.Id): Order?

  fun cancelOrder(orderId: Order.Id): Order

  fun confirmPayment(orderId: Order.Id): Order

  data class OrderItemRequest(val articleId: Article.Id, val quantity: Int)
}
