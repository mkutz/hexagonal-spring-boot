package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Unit
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour.DeliveryItem
import java.util.UUID.randomUUID

data class TourBuilder(
  var id: String = randomUUID().toString(),
  var stops: List<Tour.Stop> = List(3, { StopBuilder().build() }),
) {

  companion object {
    fun aTour() = TourBuilder()

    fun aStop() = StopBuilder()

    fun aDeliveryItem() = DeliveryItemBuilder()
  }

  fun id(id: String) = apply { this.id = id }

  fun stops(stops: List<Tour.Stop>) = apply { this.stops = stops }

  fun stops(vararg stops: Tour.Stop) = stops(stops.toList())

  fun withStops(count: Int) = stops(List(count, { StopBuilder().build() }))

  fun build() = Tour(id = id, stops = stops)

  data class StopBuilder(
    var customerName: String = "Default Customer",
    var address: String = "Some Address",
    var longitude: String = "0.0",
    var latitude: String = "0.0",
    var deliveryItems: List<DeliveryItem> = List(7, { aDeliveryItem().build() }),
  ) {

    fun customerName(customerName: String) = apply { this.customerName = customerName }

    fun address(address: String) = apply { this.address = address }

    fun longitude(longitude: String) = apply { this.longitude = longitude }

    fun latitude(latitude: String) = apply { this.latitude = latitude }

    fun deliveryItems(deliveryItems: List<DeliveryItem>) = apply {
      this.deliveryItems = deliveryItems
    }

    fun deliveryItems(vararg deliveryItems: DeliveryItem) = deliveryItems(deliveryItems.toList())

    fun withDeliveryItems(count: Int) =
      deliveryItems(List(count, { DeliveryItemBuilder().build() }))

    fun build() =
      Tour.Stop(
        customerName = customerName,
        address = address,
        longitude = longitude,
        latitude = latitude,
        deliveryItems = deliveryItems,
      )
  }

  data class DeliveryItemBuilder(
    var articleId: String = randomUUID().toString(),
    var quantity: Int = 1,
    var unit: Unit = Unit.PIECE,
  ) {
    fun articleId(articleId: String) = apply { this.articleId = articleId }

    fun quantity(quantity: Int) = apply { this.quantity = quantity }

    fun unit(unit: Unit) = apply { this.unit = unit }

    fun build() = DeliveryItem(articleId = articleId, quantity = quantity, unit = unit)
  }
}
