package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.order.Order

class ToPublishOrderMessagesStub : ToPublishOrderMessages {
  private val createdMessages = mutableListOf<Order>()
  private val cancelledMessages = mutableListOf<Order>()
  private val paymentConfirmedMessages = mutableListOf<Order>()

  override fun publishOrderCreated(order: Order) {
    createdMessages.add(order)
  }

  override fun publishOrderCancelled(order: Order) {
    cancelledMessages.add(order)
  }

  override fun publishPaymentConfirmed(order: Order) {
    paymentConfirmedMessages.add(order)
  }

  fun getCreatedMessages(): List<Order> = createdMessages.toList()

  fun getCancelledMessages(): List<Order> = cancelledMessages.toList()

  fun getPaymentConfirmedMessages(): List<Order> = paymentConfirmedMessages.toList()

  fun clear() {
    createdMessages.clear()
    cancelledMessages.clear()
    paymentConfirmedMessages.clear()
  }
}
