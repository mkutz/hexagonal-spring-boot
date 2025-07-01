package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.http.articledata

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Article
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ToGetArticle
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class ArticleDataGetter(restClientBuilder: RestClient.Builder) : ToGetArticle {

  private val restClient: RestClient = restClientBuilder.baseUrl("http://localhost:8080").build()

  override fun getArticle(articleId: String): Article? {
    return try {
      restClient.get().uri("/articles/$articleId").retrieve().body<ArticleDataDto>()?.toArticle()
    } catch (e: Exception) {
      throw ArticleDataRetrievalException(e)
    }
  }
}
