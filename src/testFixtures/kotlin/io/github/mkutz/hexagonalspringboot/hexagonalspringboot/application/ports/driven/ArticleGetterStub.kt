package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Article
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.ArticleBuilder.Companion.anArticle
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.tour.Tour

class ArticleGetterStub(private val articles: MutableMap<String, Article> = HashMap()) :
  ToGetArticle {

  override fun getArticle(articleId: String) = articles[articleId]

  fun addArticlesForTour(tour: Tour) {
    tour.stops.flatMap { it.deliveryItems }.map { anArticle().id(it.articleId).build() }
  }

  fun addArticle(article: Article): Article {
    articles.put(article.id, article)
    return article
  }
}
