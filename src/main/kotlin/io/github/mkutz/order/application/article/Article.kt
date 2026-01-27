package io.github.mkutz.order.application.article

import java.util.UUID

data class Article(val id: Id, val name: String, val price: Money) {
  init {
    require(name.isNotBlank()) { "Article name must not be blank" }
  }

  @JvmInline
  value class Id(val value: UUID) {
    companion object {
      fun generate(): Id = Id(UUID.randomUUID())
    }

    override fun toString(): String = value.toString()
  }
}
