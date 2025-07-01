package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driving.kafka.tour

import com.fasterxml.jackson.databind.ObjectMapper
import org.approvej.ApprovalBuilder.approve
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

class TourMessageTest {

  private val objectMapper = Jackson2ObjectMapperBuilder.json().build<ObjectMapper>()

  @Test
  fun parse() {
    val messageString =
      """
        {
          "id" : "00000000-0000-0000-0000-000000000001",
          "stops" : [ {
            "oderId" : "00000000-0000-0000-0000-000000000002",
            "customerName" : "Some Name",
            "address" : "Some address",
            "longitude" : "0.1",
            "latitude" : "0.2",
            "deliveryItems" : [ {
              "articleId" : "00000000-0000-0000-0000-000000000003",
              "quantity" : 2,
              "unit" : "KILOGRAM"
            } ]
          } ]
        }
        """
        .trimIndent()

    val tourMessage = objectMapper.readValue(messageString, TourMessage::class.java)

    approve(tourMessage).byFile()
  }

  @Test
  fun toTour() {
    val message =
      TourMessage(
        id = "00000000-0000-0000-0000-000000000001",
        stops =
          listOf(
            TourMessage.Stop(
              oderId = "00000000-0000-0000-0000-000000000002",
              customerName = "Some Name",
              address = "Some address",
              longitude = "0.1",
              latitude = "0.2",
              deliveryItems =
                listOf(
                  TourMessage.DeliveryItem(
                    articleId = "00000000-0000-0000-0000-000000000003",
                    quantity = 2,
                    unit = "KILOGRAM",
                  )
                ),
            )
          ),
      )

    val tour = message.toTour()

    assertThat(tour.id).isEqualTo(message.id)
    approve(tour).byFile()
  }
}
