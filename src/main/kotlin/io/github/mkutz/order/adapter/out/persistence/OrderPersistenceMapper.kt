package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.article.Money
import io.github.mkutz.order.application.order.Order
import java.util.Currency
import org.springframework.stereotype.Component

@Component
class OrderPersistenceMapper {

  fun toEntity(order: Order): OrderJpaEntity {
    val entity = OrderJpaEntity(id = order.id.value, status = toStatusJpa(order.status))
    order.items.forEach { item ->
      val itemEntity = toItemEntity(item)
      itemEntity.order = entity
      entity.items.add(itemEntity)
    }
    return entity
  }

  fun toDomain(entity: OrderJpaEntity): Order {
    return Order(
      id = Order.Id(entity.id),
      status = toStatus(entity.status),
      items = entity.items.map { toDomainItem(it) },
    )
  }

  private fun toItemEntity(item: Order.Item): OrderItemJpaEntity {
    return OrderItemJpaEntity(
      articleId = item.articleId.value,
      articleName = item.articleName,
      quantity = item.quantity,
      unitPriceAmount = item.unitPrice.amount,
      unitPriceCurrency = item.unitPrice.currency.currencyCode,
    )
  }

  private fun toDomainItem(entity: OrderItemJpaEntity): Order.Item {
    return Order.Item(
      articleId = Article.Id(entity.articleId),
      articleName = entity.articleName,
      quantity = entity.quantity,
      unitPrice = Money(entity.unitPriceAmount, Currency.getInstance(entity.unitPriceCurrency)),
    )
  }

  private fun toStatusJpa(status: Order.Status): OrderStatusJpa {
    return when (status) {
      Order.Status.CREATED -> OrderStatusJpa.CREATED
      Order.Status.PAYMENT_CONFIRMED -> OrderStatusJpa.PAYMENT_CONFIRMED
      Order.Status.SHIPPED -> OrderStatusJpa.SHIPPED
      Order.Status.DELIVERED -> OrderStatusJpa.DELIVERED
      Order.Status.CANCELLED -> OrderStatusJpa.CANCELLED
    }
  }

  private fun toStatus(status: OrderStatusJpa): Order.Status {
    return when (status) {
      OrderStatusJpa.CREATED -> Order.Status.CREATED
      OrderStatusJpa.PAYMENT_CONFIRMED -> Order.Status.PAYMENT_CONFIRMED
      OrderStatusJpa.SHIPPED -> Order.Status.SHIPPED
      OrderStatusJpa.DELIVERED -> Order.Status.DELIVERED
      OrderStatusJpa.CANCELLED -> Order.Status.CANCELLED
    }
  }
}
