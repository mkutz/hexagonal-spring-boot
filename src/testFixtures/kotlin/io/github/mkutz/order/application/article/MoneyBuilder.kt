package io.github.mkutz.order.application.article

import java.math.BigDecimal
import java.util.Currency

class MoneyBuilder {
  var amount: BigDecimal = BigDecimal("10.00")
  var currency: Currency = Currency.getInstance("EUR")

  fun build() = Money(amount = amount, currency = currency)
}

fun someMoney(init: MoneyBuilder.() -> Unit = {}) = MoneyBuilder().apply(init).build()
