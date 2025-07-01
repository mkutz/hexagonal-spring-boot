package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Unit

data class Tour(val id: String, val stops: List<Stop>) {

  data class Stop(
    val customerName: String,
    val address: String,
    val longitude: String,
    val latitude: String,
    val deliveryItems: List<DeliveryItem>,
  )

  data class DeliveryItem(val articleId: String, val quantity: Int, val unit: Unit)
}
