package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist

data class DeliveryList(
  val id: String,
  val customerName: String,
  val customerAddress: String,
  val deliveryItems: List<DelivereyItem>,
) {
  data class DelivereyItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val unit: String,
    val pricePerUnit: Int,
  ) {
    val totalPrice = quantity * pricePerUnit
  }
}
