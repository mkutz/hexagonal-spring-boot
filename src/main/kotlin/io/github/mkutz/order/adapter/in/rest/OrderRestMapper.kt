package io.github.mkutz.order.adapter.`in`.rest

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.port.`in`.ToManageOrders
import java.util.UUID

object OrderRestMapper {

  fun toOrderItemRequests(request: CreateOrderRequest): List<ToManageOrders.OrderItemRequest> {
    return request.items.map { item ->
      ToManageOrders.OrderItemRequest(
        articleId = Article.Id(UUID.fromString(item.articleId)),
        quantity = item.quantity,
      )
    }
  }

  fun toResponse(order: Order): OrderResponse {
    return OrderResponse(
      id = order.id.value.toString(),
      items =
        order.items.map { item ->
          OrderItemResponse(
            articleId = item.articleId.value.toString(),
            articleName = item.articleName,
            quantity = item.quantity,
            unitPrice = item.unitPrice.amount,
            totalPrice = item.totalPrice.amount,
          )
        },
      status = order.status.name,
      totalAmount = order.totalPrice.amount,
      currency = order.totalPrice.currency.currencyCode,
    )
  }
}
