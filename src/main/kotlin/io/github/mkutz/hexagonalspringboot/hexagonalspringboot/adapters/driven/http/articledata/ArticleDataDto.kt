package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.http.articledata

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Article
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Unit

data class ArticleDataDto(
  val id: String,
  val title: String,
  val information: String,
  val pricePerUnit: Int,
  val unit: String,
) {

  fun toArticle() =
    Article(
      id = id,
      name = title,
      description = information,
      pricePerUnit = pricePerUnit,
      unit =
        when (unit.lowercase()) {
          "kg" -> Unit.KILOGRAM
          "g" -> Unit.GRAM
          "l" -> Unit.LITER
          "ml" -> Unit.MILLILITER
          "m" -> Unit.METER
          "cm" -> Unit.CENTIMETER
          "mm" -> Unit.MILLIMETER
          else -> Unit.PIECE
        },
    )
}
