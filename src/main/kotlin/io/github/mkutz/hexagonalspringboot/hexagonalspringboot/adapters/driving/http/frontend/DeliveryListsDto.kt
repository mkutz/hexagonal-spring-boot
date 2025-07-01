package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driving.http.frontend

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist.DeliveryList
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist.DeliveryList.DelivereyItem

data class DeliveryListsDto(val deliveryLists: List<DeliveryListDto>) {

  data class DeliveryListDto(
    val id: String,
    val customerName: String,
    val customerAddress: String,
    val deliveredItems: List<DeliveredItemDto>,
  ) {
    constructor(
      deliveryList: DeliveryList
    ) : this(
      id = deliveryList.id,
      customerName = deliveryList.customerName,
      customerAddress = deliveryList.customerAddress,
      deliveredItems = deliveryList.deliveryItems.map { DeliveredItemDto(it) },
    )
  }

  data class DeliveredItemDto(
    val id: String,
    val name: String,
    val quantity: String,
    val pricePerUnit: String,
    val totalPrice: String,
  ) {
    constructor(
      delivereyItem: DelivereyItem
    ) : this(
      id = delivereyItem.id,
      name = delivereyItem.name,
      quantity = "${delivereyItem.quantity} ${delivereyItem.unit}",
      pricePerUnit = (delivereyItem.pricePerUnit / 100).toString(2),
      totalPrice = (delivereyItem.totalPrice / 100).toString(2),
    )
  }
}
