package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.port.out.ToLoadOrders
import io.github.mkutz.order.application.port.out.ToSaveOrders
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Repository

@Repository
class OrderPersistenceAdapter(
  private val orderJpaRepository: OrderJpaRepository,
  private val mapper: OrderPersistenceMapper,
) : ToLoadOrders, ToSaveOrders {

  override fun loadOrder(orderId: Order.Id): Order? {
    return orderJpaRepository.findById(orderId.value).getOrNull()?.let { mapper.toDomain(it) }
  }

  override fun saveOrder(order: Order): Order {
    val entity = mapper.toEntity(order)
    val savedEntity = orderJpaRepository.save(entity)
    return mapper.toDomain(savedEntity)
  }
}
