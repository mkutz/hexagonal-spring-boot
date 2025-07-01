package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist

import java.util.UUID

data class DeliveryListBuilder(
  val id: String = "default-id",
  val customerName: String = "Default Customer",
  val customerAddress: String = "123 Default St, Default City",
  val deliveryItems: List<DeliveryList.DelivereyItem> = emptyList(),
) {

  companion object {
    fun aDeliveryList() = DeliveryListBuilder()

    fun aDeliveryItem() = DeliveryItemBuilder()
  }

  fun build() =
    DeliveryList(
      id = id,
      customerName = customerName,
      customerAddress = customerAddress,
      deliveryItems = deliveryItems,
    )

  data class DeliveryItemBuilder(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "Some item",
    val quantity: Int = 1,
    val unit: String = "pcs",
    val pricePerUnit: Int = 299,
  ) {

    fun build() =
      DeliveryList.DelivereyItem(
        id = id,
        name = name,
        quantity = quantity,
        unit = unit,
        pricePerUnit = pricePerUnit,
      )
  }
}
