package io.github.mkutz.order.adapter.out.persistence

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "orders")
class OrderJpaEntity(
  @Id val id: UUID,
  @Enumerated(EnumType.STRING) @Column(nullable = false) var status: OrderStatusJpa,
  @OneToMany(
    mappedBy = "order",
    cascade = [CascadeType.ALL],
    orphanRemoval = true,
    fetch = FetchType.EAGER,
  )
  val items: MutableList<OrderItemJpaEntity> = mutableListOf(),
)

enum class OrderStatusJpa {
  CREATED,
  PAYMENT_CONFIRMED,
  SHIPPED,
  DELIVERED,
  CANCELLED,
}
