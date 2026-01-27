package io.github.mkutz.order.adapter.out.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "articles")
class ArticleEntity(
  @Id val id: UUID,
  @Column(nullable = false) val name: String,
  @Column(nullable = false) val priceAmount: BigDecimal,
  @Column(nullable = false, length = 3) val priceCurrency: String,
)
