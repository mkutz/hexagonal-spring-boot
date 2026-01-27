package io.github.mkutz.order.application.article

import java.math.BigDecimal
import java.util.Currency

data class Money(val amount: BigDecimal, val currency: Currency) {
  init {
    require(amount >= BigDecimal.ZERO) { "Amount must be non-negative" }
  }

  operator fun plus(other: Money): Money {
    require(currency == other.currency) { "Cannot add different currencies" }
    return Money(amount + other.amount, currency)
  }

  operator fun times(quantity: Int): Money {
    require(quantity >= 0) { "Quantity must be non-negative" }
    return Money(amount * BigDecimal(quantity), currency)
  }

  companion object {
    fun eur(amount: BigDecimal): Money = Money(amount, Currency.getInstance("EUR"))

    fun eur(amount: String): Money = eur(BigDecimal(amount))
  }
}
