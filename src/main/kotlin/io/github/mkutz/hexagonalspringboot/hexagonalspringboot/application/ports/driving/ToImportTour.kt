package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driving

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour

interface ToImportTour {

  fun importTour(tour: Tour)
}
