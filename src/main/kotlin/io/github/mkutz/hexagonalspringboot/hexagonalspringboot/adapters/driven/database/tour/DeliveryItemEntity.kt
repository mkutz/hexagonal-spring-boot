package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.database.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Unit
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "delivery_item")
data class DeliveryItemEntity(
  @Id val id: String,
  val tourStopId: String,
  val articleId: String,
  val quantity: Int,
  val unit: String,
) {
  constructor(
    id: String,
    tourStopId: String,
    item: Tour.DeliveryItem,
  ) : this(
    id = id,
    tourStopId = tourStopId,
    articleId = item.articleId,
    quantity = item.quantity,
    unit = item.unit.name,
  )

  fun toDeliveryItem() =
    Tour.DeliveryItem(articleId = articleId, quantity = quantity, unit = Unit.valueOf(unit))
}
