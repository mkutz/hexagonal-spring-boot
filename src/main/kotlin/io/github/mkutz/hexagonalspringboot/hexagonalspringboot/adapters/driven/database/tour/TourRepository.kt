package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.database.tour

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository interface TourRepository : CrudRepository<TourEntity, String>
