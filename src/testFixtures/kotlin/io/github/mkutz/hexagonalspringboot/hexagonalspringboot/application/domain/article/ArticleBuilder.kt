package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article

import java.util.UUID

data class ArticleBuilder(
  var id: String = UUID.randomUUID().toString(),
  var name: String = "Some name",
  var description: String = "Some article",
  var pricePerUnit: Int = 4,
  var unit: Unit = Unit.PIECE,
) {
  companion object {
    fun anArticle() = ArticleBuilder()
  }

  fun id(id: String) = apply { this.id = id }

  fun name(name: String) = apply { this.name = name }

  fun description(description: String) = apply { this.description = description }

  fun pricePerUnit(pricePerUnit: Int) = apply { this.pricePerUnit = pricePerUnit }

  fun unit(unit: Unit) = apply { this.unit = unit }

  fun build() =
    Article(
      id = id,
      name = name,
      description = description,
      pricePerUnit = pricePerUnit,
      unit = unit,
    )
}
