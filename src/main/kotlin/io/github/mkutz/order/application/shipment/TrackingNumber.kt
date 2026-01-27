package io.github.mkutz.order.application.shipment

@JvmInline
value class TrackingNumber(val value: String) {
  init {
    require(value.isNotBlank()) { "Tracking number must not be blank" }
  }

  override fun toString(): String = value
}
