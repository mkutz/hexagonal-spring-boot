package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour

class TourStoreStub(private val tours: MutableMap<String, Tour> = HashMap()) : ToStoreTour {

  override fun storeTour(tour: Tour): Tour {
    tours.put(tour.id, tour)
    return tour
  }

  override fun getTourById(id: String) = tours[id]

  override fun getAllTours(): List<Tour> = tours.values.toList()
}
