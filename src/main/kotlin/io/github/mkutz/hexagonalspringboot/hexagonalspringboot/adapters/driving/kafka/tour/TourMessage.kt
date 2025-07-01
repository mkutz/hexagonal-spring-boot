package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driving.kafka.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Unit
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour

data class TourMessage(val id: String, val stops: List<Stop>) {

  fun toTour() = Tour(id = this.id, stops = this.stops.map { it.toStop() })

  data class Stop(
    val oderId: String,
    val customerName: String,
    val address: String,
    val longitude: String,
    val latitude: String,
    val deliveryItems: List<DeliveryItem>,
  ) {
    fun toStop() =
      Tour.Stop(
        customerName = customerName,
        address = address,
        longitude = longitude,
        latitude = latitude,
        deliveryItems = deliveryItems.map { it.toDeliveryItem() },
      )
  }

  data class DeliveryItem(val articleId: String, val quantity: Int, val unit: String) {
    fun toDeliveryItem() =
      Tour.DeliveryItem(articleId = articleId, quantity = quantity, unit = Unit.valueOf(unit))
  }
}
