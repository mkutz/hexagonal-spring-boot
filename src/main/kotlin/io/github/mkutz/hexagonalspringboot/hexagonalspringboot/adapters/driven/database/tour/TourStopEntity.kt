package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.database.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID.randomUUID
import org.apache.tomcat.jni.Buffer.address

@Entity
@Table(name = "tour_stop")
data class TourStopEntity(
  @Id val id: String,
  val tourId: String,
  val customerName: String,
  val address: String,
  val longitude: String,
  val latitude: String,
  @OneToMany(mappedBy = "itemId") val deliveryItems: List<DeliveryItemEntity>,
) {
  constructor(
    id: String,
    tourId: String,
    stop: Tour.Stop,
  ) : this(
    id = id,
    tourId = tourId,
    customerName = stop.customerName,
    address = stop.address,
    longitude = stop.longitude,
    latitude = stop.latitude,
    deliveryItems =
      stop.deliveryItems.mapIndexed { index, item ->
        DeliveryItemEntity(id = randomUUID().toString(), tourStopId = id, item = item)
      },
  )

  fun toTourStop() =
    Tour.Stop(
      customerName = customerName,
      address = address,
      longitude = longitude,
      latitude = latitude,
      deliveryItems = deliveryItems.map { it.toDeliveryItem() },
    )
}
