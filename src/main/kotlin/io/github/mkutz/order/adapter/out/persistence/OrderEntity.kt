package io.github.mkutz.order.adapter.out.persistence

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "orders")
class OrderEntity(
  @Id val id: UUID,
  @Enumerated(EnumType.STRING) @Column(nullable = false) var status: Status,
  @OneToMany(
    mappedBy = "order",
    cascade = [CascadeType.ALL],
    orphanRemoval = true,
    fetch = FetchType.EAGER,
  )
  val items: MutableList<ItemEntity> = mutableListOf(),
) {
  enum class Status {
    CREATED,
    PAYMENT_CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
  }

  @Entity
  @Table(name = "order_items")
  class ItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrderEntity? = null,
    @Column(nullable = false) val articleId: UUID,
    @Column(nullable = false) val articleName: String,
    @Column(nullable = false) val quantity: Int,
    @Column(nullable = false) val unitPriceAmount: BigDecimal,
    @Column(nullable = false, length = 3) val unitPriceCurrency: String,
  )
}
