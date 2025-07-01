package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driving

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist.DeliveryList

interface ToGetDeliveryList {

  fun getAllDeliveryLists(tourId: String): List<DeliveryList>
}
