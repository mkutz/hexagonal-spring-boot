package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.database.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID.randomUUID

@Entity
@Table(name = "tour")
data class TourEntity(
  @Id val id: String,
  @OneToMany(mappedBy = "tourId", orphanRemoval = true, cascade = [CascadeType.ALL])
  val stops: List<TourStopEntity>,
) {
  constructor(
    tour: Tour
  ) : this(
    id = tour.id,
    stops =
      tour.stops.map { TourStopEntity(id = randomUUID().toString(), tourId = tour.id, stop = it) },
  )

  fun toTour() =
    Tour(
      id = id,
      stops =
        stops.map {
          Tour.Stop(
            customerName = it.customerName,
            address = it.address,
            longitude = it.longitude,
            latitude = it.latitude,
            deliveryItems = it.deliveryItems.map { deliveryItem -> deliveryItem.toDeliveryItem() },
          )
        },
    )
}
