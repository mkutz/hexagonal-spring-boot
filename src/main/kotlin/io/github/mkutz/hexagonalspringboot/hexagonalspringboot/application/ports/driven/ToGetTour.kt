package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour

interface ToGetTour {

  fun getTourById(id: String): Tour?

  fun getAllTours(): List<Tour>
}
