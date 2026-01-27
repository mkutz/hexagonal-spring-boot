package io.github.mkutz.order.adapter.out.persistence

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<ArticleEntity, UUID>
