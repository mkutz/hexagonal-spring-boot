package io.github.mkutz.order.adapter.out.persistence

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, UUID>
