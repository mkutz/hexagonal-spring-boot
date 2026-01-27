package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.article.Money
import io.github.mkutz.order.application.order.Order
import java.util.Currency

object OrderPersistenceMapper {

  fun toEntity(order: Order): OrderEntity {
    val entity = OrderEntity(id = order.id.value, status = toEntityStatus(order.status))
    order.items.forEach { item ->
      val itemEntity = toItemEntity(item)
      itemEntity.order = entity
      entity.items.add(itemEntity)
    }
    return entity
  }

  fun toDomain(entity: OrderEntity): Order {
    return Order(
      id = Order.Id(entity.id),
      status = toStatus(entity.status),
      items = entity.items.map { toDomainItem(it) },
    )
  }

  private fun toItemEntity(item: Order.Item): OrderEntity.ItemEntity {
    return OrderEntity.ItemEntity(
      articleId = item.articleId.value,
      articleName = item.articleName,
      quantity = item.quantity,
      unitPriceAmount = item.unitPrice.amount,
      unitPriceCurrency = item.unitPrice.currency.currencyCode,
    )
  }

  private fun toDomainItem(entity: OrderEntity.ItemEntity): Order.Item {
    return Order.Item(
      articleId = Article.Id(entity.articleId),
      articleName = entity.articleName,
      quantity = entity.quantity,
      unitPrice = Money(entity.unitPriceAmount, Currency.getInstance(entity.unitPriceCurrency)),
    )
  }

  private fun toEntityStatus(status: Order.Status): OrderEntity.Status {
    return when (status) {
      Order.Status.CREATED -> OrderEntity.Status.CREATED
      Order.Status.PAYMENT_CONFIRMED -> OrderEntity.Status.PAYMENT_CONFIRMED
      Order.Status.SHIPPED -> OrderEntity.Status.SHIPPED
      Order.Status.DELIVERED -> OrderEntity.Status.DELIVERED
      Order.Status.CANCELLED -> OrderEntity.Status.CANCELLED
    }
  }

  private fun toStatus(status: OrderEntity.Status): Order.Status {
    return when (status) {
      OrderEntity.Status.CREATED -> Order.Status.CREATED
      OrderEntity.Status.PAYMENT_CONFIRMED -> Order.Status.PAYMENT_CONFIRMED
      OrderEntity.Status.SHIPPED -> Order.Status.SHIPPED
      OrderEntity.Status.DELIVERED -> Order.Status.DELIVERED
      OrderEntity.Status.CANCELLED -> Order.Status.CANCELLED
    }
  }
}
