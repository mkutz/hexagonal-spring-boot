package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.deliverylist

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.ArticleService
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.TourService
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driving.ToGetDeliveryList
import org.springframework.stereotype.Service

@Service
class DeliveryListService(
  private val tourService: TourService,
  private val articleService: ArticleService,
) : ToGetDeliveryList {

  override fun getAllDeliveryLists(tourId: String) =
    tourService.getTour(tourId)?.stops?.mapIndexed { index, stop ->
      DeliveryList(
        id = "$tourId-$index",
        customerName = stop.customerName,
        customerAddress = stop.address,
        deliveryItems =
          stop.deliveryItems.map { deliveryItem ->
            val article = articleService.getArticle(deliveryItem.articleId)
            DeliveryList.DelivereyItem(
              id = deliveryItem.articleId,
              name = article?.name ?: "-",
              quantity = deliveryItem.quantity,
              unit = deliveryItem.unit.name,
              pricePerUnit = article?.pricePerUnit ?: 0,
            )
          },
      )
    } ?: emptyList()
}
