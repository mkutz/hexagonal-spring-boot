package io.github.mkutz.order.application.order

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.article.Money
import java.math.BigDecimal
import java.util.UUID

data class Order(val id: Id, val items: List<Item>, val status: Status) {
  init {
    require(items.isNotEmpty()) { "Order must have at least one item" }
  }

  val totalPrice: Money
    get() {
      val currency = items.first().unitPrice.currency
      return items.fold(Money(BigDecimal.ZERO, currency)) { acc, item -> acc + item.totalPrice }
    }

  fun confirmPayment(): Order {
    check(status == Status.CREATED) {
      "Can only confirm payment for orders in CREATED status, but was $status"
    }
    return copy(status = Status.PAYMENT_CONFIRMED)
  }

  fun ship(): Order {
    check(status == Status.PAYMENT_CONFIRMED) {
      "Can only ship orders in PAYMENT_CONFIRMED status, but was $status"
    }
    return copy(status = Status.SHIPPED)
  }

  fun deliver(): Order {
    check(status == Status.SHIPPED) { "Can only deliver orders in SHIPPED status, but was $status" }
    return copy(status = Status.DELIVERED)
  }

  fun cancel(): Order {
    check(status == Status.CREATED || status == Status.PAYMENT_CONFIRMED) {
      "Can only cancel orders in CREATED or PAYMENT_CONFIRMED status, but was $status"
    }
    return copy(status = Status.CANCELLED)
  }

  @JvmInline
  value class Id(val value: UUID) {
    companion object {
      fun generate(): Id = Id(UUID.randomUUID())
    }

    override fun toString(): String = value.toString()
  }

  data class Item(
    val articleId: Article.Id,
    val articleName: String,
    val quantity: Int,
    val unitPrice: Money,
  ) {
    init {
      require(quantity > 0) { "Quantity must be positive" }
      require(articleName.isNotBlank()) { "Article name must not be blank" }
    }

    val totalPrice: Money
      get() = unitPrice * quantity
  }

  enum class Status {
    CREATED,
    PAYMENT_CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
  }
}
