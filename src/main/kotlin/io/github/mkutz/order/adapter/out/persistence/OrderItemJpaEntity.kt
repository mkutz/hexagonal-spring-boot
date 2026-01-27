package io.github.mkutz.order.adapter.out.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "order_items")
class OrderItemJpaEntity(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  var order: OrderJpaEntity? = null,
  @Column(nullable = false) val articleId: UUID,
  @Column(nullable = false) val articleName: String,
  @Column(nullable = false) val quantity: Int,
  @Column(nullable = false) val unitPriceAmount: BigDecimal,
  @Column(nullable = false, length = 3) val unitPriceCurrency: String,
)
