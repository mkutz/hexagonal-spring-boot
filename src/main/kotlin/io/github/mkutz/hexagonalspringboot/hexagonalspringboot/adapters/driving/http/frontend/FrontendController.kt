package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driving.http.frontend

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist.DeliveryListService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/api/frontend/delivery-lists")
class FrontendController(private val deliveryListService: DeliveryListService) {

  @GetMapping("/{tourId}")
  fun getDeliveryLists(@PathVariable tourId: String) =
    DeliveryListsDto(
      deliveryListService.getAllDeliveryLists(tourId).map { DeliveryListsDto.DeliveryListDto(it) }
    )
}
