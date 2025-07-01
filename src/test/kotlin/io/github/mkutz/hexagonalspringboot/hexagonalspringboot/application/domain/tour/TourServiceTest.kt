package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.TourBuilder.Companion.aTour
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.TourStoreStub
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TourServiceTest {

  private val tourStoreStub = TourStoreStub()
  private val tourService = TourService(tourStoreStub)

  @Test
  fun getAllTours() {
    val storedTour = tourStoreStub.storeTour(aTour().build())

    val tour = tourService.getTour(storedTour.id)

    assertThat(tour).isEqualTo(storedTour)
  }

  @Test
  fun `getAllTours unknown tour ID`() {
    val tour = tourService.getTour(UUID.randomUUID().toString())

    assertThat(tour).isNull()
  }
}
