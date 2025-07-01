package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.database.tour

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ToGetTour
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ToStoreTour
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TourStore(private val repository: TourRepository) : ToStoreTour, ToGetTour {

  override fun storeTour(tour: Tour) = repository.save(TourEntity(tour)).toTour()

  override fun getTourById(id: String) = repository.findByIdOrNull(id)?.toTour()

  override fun getAllTours() = repository.findAll().map { it.toTour() }
}
