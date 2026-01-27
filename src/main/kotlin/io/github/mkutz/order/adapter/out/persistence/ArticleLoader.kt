package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.port.out.ToLoadArticles
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Repository

@Repository
class ArticleLoader(private val articleRepository: ArticleRepository) : ToLoadArticles {

  override fun loadArticle(articleId: Article.Id): Article? {
    return articleRepository.findById(articleId.value).getOrNull()?.let {
      ArticlePersistenceMapper.toDomain(it)
    }
  }

  override fun loadArticles(articleIds: List<Article.Id>): List<Article> {
    val uuids = articleIds.map { it.value }
    return articleRepository.findAllById(uuids).map { ArticlePersistenceMapper.toDomain(it) }
  }
}
