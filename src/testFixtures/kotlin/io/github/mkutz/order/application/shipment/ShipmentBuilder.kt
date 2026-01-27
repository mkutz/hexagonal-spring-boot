package io.github.mkutz.order.application.shipment

import io.github.mkutz.order.application.order.Order
import java.util.UUID

class ShipmentBuilder {
  var id: ShipmentId = ShipmentId(UUID.randomUUID())
  var orderId: Order.Id = Order.Id(UUID.randomUUID())
  var trackingNumber: TrackingNumber =
    TrackingNumber("TRACK-${UUID.randomUUID().toString().take(8).uppercase()}")
  var status: ShipmentStatus = ShipmentStatus.PENDING

  fun build() =
    Shipment(id = id, orderId = orderId, trackingNumber = trackingNumber, status = status)
}

fun aShipment(init: ShipmentBuilder.() -> Unit = {}) = ShipmentBuilder().apply(init).build()
