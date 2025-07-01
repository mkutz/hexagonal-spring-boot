package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article

data class Article(
  val id: String,
  val name: String,
  val description: String,
  val pricePerUnit: Int,
  val unit: Unit,
)
