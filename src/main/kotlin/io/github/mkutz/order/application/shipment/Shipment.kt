package io.github.mkutz.order.application.shipment

import io.github.mkutz.order.application.order.Order
import java.util.UUID

data class Shipment(
  val id: Id,
  val orderId: Order.Id,
  val trackingNumber: TrackingNumber,
  val status: ShipmentStatus,
) {
  fun markInTransit(): Shipment {
    check(status == ShipmentStatus.PENDING) {
      "Can only mark PENDING shipments as in transit, but was $status"
    }
    return copy(status = ShipmentStatus.IN_TRANSIT)
  }

  fun markDelivered(): Shipment {
    check(status == ShipmentStatus.IN_TRANSIT) {
      "Can only mark IN_TRANSIT shipments as delivered, but was $status"
    }
    return copy(status = ShipmentStatus.DELIVERED)
  }

  fun markFailed(): Shipment {
    check(status == ShipmentStatus.PENDING || status == ShipmentStatus.IN_TRANSIT) {
      "Can only mark PENDING or IN_TRANSIT shipments as failed, but was $status"
    }
    return copy(status = ShipmentStatus.FAILED)
  }

  @JvmInline
  value class Id(val value: UUID) {
    companion object {
      fun generate(): Id = Id(UUID.randomUUID())
    }

    override fun toString(): String = value.toString()
  }
}
