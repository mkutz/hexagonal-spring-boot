package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driving.kafka.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driving.ToImportTour
import org.springframework.kafka.annotation.KafkaListener

class TourConsumer(private val tourImporter: ToImportTour) {

  @KafkaListener
  fun consume(message: TourMessage) {
    tourImporter.importTour(message.toTour())
  }
}
