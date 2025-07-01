package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.ArticleService
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.TourBuilder.Companion.aTour
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.TourService
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ArticleGetterStub
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.TourStoreStub
import java.util.UUID
import org.approvej.ApprovalBuilder.approve
import org.approvej.scrub.Scrubbers.uuids
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DeliveryListServiceTest {

  private val tourStoreStub = TourStoreStub()
  private val articleGetterStub = ArticleGetterStub()
  private val deliveryListService =
    DeliveryListService(TourService(tourStoreStub), ArticleService(articleGetterStub))

  @Test
  fun getAllDeliveryLists() {
    val storedTour = tourStoreStub.storeTour(aTour().build())
    articleGetterStub.addArticlesForTour(storedTour)

    val deliveryLists = deliveryListService.getAllDeliveryLists(storedTour.id)

    assertThat(deliveryLists).hasSameSizeAs(storedTour.stops)
    approve(deliveryLists).print().scrubbedOf(uuids()).byFile()
  }

  @Test
  fun `getAllDeliveryLists unknown tour ID`() {
    val deliveryLists = deliveryListService.getAllDeliveryLists(UUID.randomUUID().toString())

    assertThat(deliveryLists).isEmpty()
  }
}
