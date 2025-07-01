package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ToGetTour
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ToStoreTour
import org.springframework.stereotype.Service

@Service
class TourService(private val tourGetter: ToGetTour) {

  fun getTour(id: String) = tourGetter.getTourById(id)
}
