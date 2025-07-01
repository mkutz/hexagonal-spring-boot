package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour

interface ToStoreTour {

  fun storeTour(tour: Tour): Tour
}
