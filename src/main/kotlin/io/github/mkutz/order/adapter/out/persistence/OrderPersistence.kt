package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.port.out.ToLoadOrders
import io.github.mkutz.order.application.port.out.ToSaveOrders
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Repository

@Repository
class OrderPersistence(private val orderRepository: OrderRepository) : ToLoadOrders, ToSaveOrders {

  override fun loadOrder(orderId: Order.Id): Order? {
    return orderRepository.findById(orderId.value).getOrNull()?.let {
      OrderPersistenceMapper.toDomain(it)
    }
  }

  override fun saveOrder(order: Order): Order {
    val entity = OrderPersistenceMapper.toEntity(order)
    val savedEntity = orderRepository.save(entity)
    return OrderPersistenceMapper.toDomain(savedEntity)
  }
}
