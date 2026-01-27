package io.github.mkutz.order.application

import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.order.OrderService
import io.github.mkutz.order.application.port.`in`.ToManageOrders
import io.github.mkutz.order.application.port.`in`.ToManageOrders.OrderItemRequest
import io.github.mkutz.order.application.port.out.ToLoadArticles
import io.github.mkutz.order.application.port.out.ToLoadOrders
import io.github.mkutz.order.application.port.out.ToPublishOrderMessages
import io.github.mkutz.order.application.port.out.ToSaveOrders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderApplicationService(
  private val toLoadOrders: ToLoadOrders,
  private val toSaveOrders: ToSaveOrders,
  private val toLoadArticles: ToLoadArticles,
  private val toPublishOrderMessages: ToPublishOrderMessages,
  private val orderService: OrderService,
) : ToManageOrders {

  override fun createOrder(items: List<OrderItemRequest>): Order {
    require(items.isNotEmpty()) { "Order must have at least one item" }

    val articleIds = items.map { it.articleId }
    val articles = toLoadArticles.loadArticles(articleIds)

    val missingArticles = articleIds - articles.map { it.id }.toSet()
    require(missingArticles.isEmpty()) {
      "Articles not found: ${missingArticles.joinToString { it.toString() }}"
    }

    val articleQuantities =
      items.map { request ->
        val article = articles.first { it.id == request.articleId }
        article to request.quantity
      }

    val order = orderService.createOrder(articleQuantities)
    val savedOrder = toSaveOrders.saveOrder(order)
    toPublishOrderMessages.publishOrderCreated(savedOrder)

    return savedOrder
  }

  @Transactional(readOnly = true)
  override fun getOrder(orderId: Order.Id): Order? {
    return toLoadOrders.loadOrder(orderId)
  }

  override fun cancelOrder(orderId: Order.Id): Order {
    val order =
      toLoadOrders.loadOrder(orderId) ?: throw IllegalArgumentException("Order not found: $orderId")

    val cancelledOrder = order.cancel()
    val savedOrder = toSaveOrders.saveOrder(cancelledOrder)
    toPublishOrderMessages.publishOrderCancelled(savedOrder)

    return savedOrder
  }

  override fun confirmPayment(orderId: Order.Id): Order {
    val order =
      toLoadOrders.loadOrder(orderId) ?: throw IllegalArgumentException("Order not found: $orderId")

    val confirmedOrder = order.confirmPayment()
    val savedOrder = toSaveOrders.saveOrder(confirmedOrder)
    toPublishOrderMessages.publishPaymentConfirmed(savedOrder)

    return savedOrder
  }
}
