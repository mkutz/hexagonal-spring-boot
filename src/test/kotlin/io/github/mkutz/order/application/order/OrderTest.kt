package io.github.mkutz.order.application.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class OrderTest {

  @Test
  fun `confirmPayment changes status to PAYMENT_CONFIRMED`() {
    val order = anOrder { status = Order.Status.CREATED }

    val confirmedOrder = order.confirmPayment()

    assertThat(confirmedOrder.status).isEqualTo(Order.Status.PAYMENT_CONFIRMED)
  }

  @Test
  fun `confirmPayment fails when order is not in CREATED status`() {
    val order = anOrder { status = Order.Status.SHIPPED }

    assertThatThrownBy { order.confirmPayment() }
      .isInstanceOf(IllegalStateException::class.java)
      .hasMessageContaining("CREATED")
  }

  @Test
  fun `ship changes status to SHIPPED`() {
    val order = anOrder { status = Order.Status.PAYMENT_CONFIRMED }

    val shippedOrder = order.ship()

    assertThat(shippedOrder.status).isEqualTo(Order.Status.SHIPPED)
  }

  @Test
  fun `ship fails when order is not in PAYMENT_CONFIRMED status`() {
    val order = anOrder { status = Order.Status.CREATED }

    assertThatThrownBy { order.ship() }
      .isInstanceOf(IllegalStateException::class.java)
      .hasMessageContaining("PAYMENT_CONFIRMED")
  }

  @Test
  fun `deliver changes status to DELIVERED`() {
    val order = anOrder { status = Order.Status.SHIPPED }

    val deliveredOrder = order.deliver()

    assertThat(deliveredOrder.status).isEqualTo(Order.Status.DELIVERED)
  }

  @Test
  fun `cancel changes status to CANCELLED for CREATED order`() {
    val order = anOrder { status = Order.Status.CREATED }

    val cancelledOrder = order.cancel()

    assertThat(cancelledOrder.status).isEqualTo(Order.Status.CANCELLED)
  }

  @Test
  fun `cancel changes status to CANCELLED for PAYMENT_CONFIRMED order`() {
    val order = anOrder { status = Order.Status.PAYMENT_CONFIRMED }

    val cancelledOrder = order.cancel()

    assertThat(cancelledOrder.status).isEqualTo(Order.Status.CANCELLED)
  }

  @Test
  fun `cancel fails when order is SHIPPED`() {
    val order = anOrder { status = Order.Status.SHIPPED }

    assertThatThrownBy { order.cancel() }.isInstanceOf(IllegalStateException::class.java)
  }

  @Test
  fun `totalPrice sums all item prices`() {
    val order = anOrder {
      items =
        listOf(
          anOrderItem {
            quantity = 2
            unitPrice = io.github.mkutz.order.application.article.Money.eur("10.00")
          },
          anOrderItem {
            quantity = 1
            unitPrice = io.github.mkutz.order.application.article.Money.eur("5.00")
          },
        )
    }

    assertThat(order.totalPrice.amount).isEqualByComparingTo("25.00")
  }

  @Test
  fun `order requires at least one item`() {
    assertThatThrownBy { anOrder { items = emptyList() } }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessageContaining("at least one item")
  }
}
