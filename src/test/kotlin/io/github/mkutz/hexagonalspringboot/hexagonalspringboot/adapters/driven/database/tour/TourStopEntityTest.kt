package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.database.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.TourBuilder.Companion.aStop
import java.util.UUID.randomUUID
import org.approvej.ApprovalBuilder.approve
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TourStopEntityTest {

  @Test
  fun constructor() {
    val stop = aStop().build()
    val id = "00000000-0000-0000-0000-000000000001"
    val tourId = "00000000-0000-0000-0000-000000000002"

    val entity = TourStopEntity(id = id, tourId = tourId, stop = stop)

    approve(entity).byFile()
  }

  @Test
  fun toTourStop() {
    val stop = aStop().build()
    val id = randomUUID().toString()
    val tourId = randomUUID().toString()
    val entity = TourStopEntity(id = id, tourId = tourId, stop = stop)

    assertThat(entity.toTourStop()).isEqualTo(stop)
  }
}
