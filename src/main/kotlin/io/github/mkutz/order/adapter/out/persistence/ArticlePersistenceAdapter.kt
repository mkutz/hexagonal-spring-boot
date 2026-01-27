package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.port.out.ToLoadArticles
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Repository

@Repository
class ArticlePersistenceAdapter(
  private val articleJpaRepository: ArticleJpaRepository,
  private val mapper: ArticlePersistenceMapper,
) : ToLoadArticles {

  override fun loadArticle(articleId: Article.Id): Article? {
    return articleJpaRepository.findById(articleId.value).getOrNull()?.let { mapper.toDomain(it) }
  }

  override fun loadArticles(articleIds: List<Article.Id>): List<Article> {
    val uuids = articleIds.map { it.value }
    return articleJpaRepository.findAllById(uuids).map { mapper.toDomain(it) }
  }
}
