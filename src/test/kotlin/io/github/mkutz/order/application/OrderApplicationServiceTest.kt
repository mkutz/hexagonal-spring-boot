package io.github.mkutz.order.application

import io.github.mkutz.order.application.article.anArticle
import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.order.OrderService
import io.github.mkutz.order.application.order.anOrder
import io.github.mkutz.order.application.port.`in`.ToManageOrders.OrderItemRequest
import io.github.mkutz.order.application.port.out.ToLoadArticlesStub
import io.github.mkutz.order.application.port.out.ToLoadOrdersStub
import io.github.mkutz.order.application.port.out.ToPublishOrderMessagesStub
import io.github.mkutz.order.application.port.out.ToSaveOrdersStub
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class OrderApplicationServiceTest {

  private val loadOrderPort = ToLoadOrdersStub()
  private val saveOrderPort = ToSaveOrdersStub()
  private val loadArticlePort = ToLoadArticlesStub()
  private val publishOrderMessagePort = ToPublishOrderMessagesStub()
  private val orderService = OrderService()

  private val orderApplicationService =
    OrderApplicationService(
      toLoadOrders = loadOrderPort,
      toSaveOrders = saveOrderPort,
      toLoadArticles = loadArticlePort,
      toPublishOrderMessages = publishOrderMessagePort,
      orderService = orderService,
    )

  @BeforeEach
  fun setUp() {
    loadOrderPort.clear()
    saveOrderPort.clear()
    loadArticlePort.clear()
    publishOrderMessagePort.clear()
  }

  @Nested
  inner class CreateOrder {

    @Test
    fun `creates order and publishes message`() {
      val article = anArticle()
      loadArticlePort.givenArticle(article)

      val order = orderApplicationService.createOrder(listOf(OrderItemRequest(article.id, 2)))

      assertThat(order.status).isEqualTo(Order.Status.CREATED)
      assertThat(order.items).hasSize(1)
      assertThat(order.items[0].articleId).isEqualTo(article.id)
      assertThat(order.items[0].quantity).isEqualTo(2)

      assertThat(saveOrderPort.getSavedOrders()).hasSize(1)
      assertThat(publishOrderMessagePort.getCreatedMessages()).hasSize(1)
    }

    @Test
    fun `fails when article not found`() {
      val article = anArticle()
      // article not added to stub

      assertThatThrownBy {
          orderApplicationService.createOrder(listOf(OrderItemRequest(article.id, 1)))
        }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessageContaining("not found")
    }

    @Test
    fun `fails with empty item list`() {
      assertThatThrownBy { orderApplicationService.createOrder(emptyList()) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessageContaining("at least one item")
    }
  }

  @Nested
  inner class GetOrder {

    @Test
    fun `returns order when found`() {
      val order = anOrder()
      loadOrderPort.givenOrder(order)

      val result = orderApplicationService.getOrder(order.id)

      assertThat(result).isEqualTo(order)
    }

    @Test
    fun `returns null when order not found`() {
      val order = anOrder()
      // order not added to stub

      val result = orderApplicationService.getOrder(order.id)

      assertThat(result).isNull()
    }
  }

  @Nested
  inner class CancelOrder {

    @Test
    fun `cancels order and publishes message`() {
      val order = anOrder { status = Order.Status.CREATED }
      loadOrderPort.givenOrder(order)

      val cancelledOrder = orderApplicationService.cancelOrder(order.id)

      assertThat(cancelledOrder.status).isEqualTo(Order.Status.CANCELLED)
      assertThat(saveOrderPort.getLastSavedOrder()?.status).isEqualTo(Order.Status.CANCELLED)
      assertThat(publishOrderMessagePort.getCancelledMessages()).hasSize(1)
    }

    @Test
    fun `fails when order not found`() {
      val order = anOrder()
      // order not added to stub

      assertThatThrownBy { orderApplicationService.cancelOrder(order.id) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessageContaining("not found")
    }
  }

  @Nested
  inner class ConfirmPayment {

    @Test
    fun `confirms payment and publishes message`() {
      val order = anOrder { status = Order.Status.CREATED }
      loadOrderPort.givenOrder(order)

      val confirmedOrder = orderApplicationService.confirmPayment(order.id)

      assertThat(confirmedOrder.status).isEqualTo(Order.Status.PAYMENT_CONFIRMED)
      assertThat(saveOrderPort.getLastSavedOrder()?.status)
        .isEqualTo(Order.Status.PAYMENT_CONFIRMED)
      assertThat(publishOrderMessagePort.getPaymentConfirmedMessages()).hasSize(1)
    }

    @Test
    fun `fails when order not found`() {
      val order = anOrder()
      // order not added to stub

      assertThatThrownBy { orderApplicationService.confirmPayment(order.id) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessageContaining("not found")
    }
  }
}
