package io.github.mkutz.order.adapter.`in`.rest

import io.github.mkutz.order.application.order.Order
import io.github.mkutz.order.application.port.`in`.ToManageOrders
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
  private val toManageOrders: ToManageOrders,
  private val mapper: OrderRestMapper,
) {

  @PostMapping
  fun createOrder(@RequestBody request: CreateOrderRequest): ResponseEntity<OrderResponse> {
    val orderItems = mapper.toOrderItemRequests(request)
    val order = toManageOrders.createOrder(orderItems)
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(order))
  }

  @GetMapping("/{orderId}")
  fun getOrder(@PathVariable orderId: String): ResponseEntity<OrderResponse> {
    val order =
      toManageOrders.getOrder(Order.Id(UUID.fromString(orderId)))
        ?: return ResponseEntity.notFound().build()
    return ResponseEntity.ok(mapper.toResponse(order))
  }

  @DeleteMapping("/{orderId}")
  fun cancelOrder(@PathVariable orderId: String): ResponseEntity<OrderResponse> {
    val order = toManageOrders.cancelOrder(Order.Id(UUID.fromString(orderId)))
    return ResponseEntity.ok(mapper.toResponse(order))
  }
}
