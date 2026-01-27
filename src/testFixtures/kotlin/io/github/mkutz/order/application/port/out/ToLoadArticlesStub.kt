package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.article.Article

class ToLoadArticlesStub : ToLoadArticles {
  private val articles = mutableMapOf<Article.Id, Article>()

  fun givenArticle(article: Article) {
    articles[article.id] = article
  }

  fun givenArticles(vararg articlesToAdd: Article) {
    articlesToAdd.forEach { articles[it.id] = it }
  }

  override fun loadArticle(articleId: Article.Id): Article? {
    return articles[articleId]
  }

  override fun loadArticles(articleIds: List<Article.Id>): List<Article> {
    return articleIds.mapNotNull { articles[it] }
  }

  fun clear() {
    articles.clear()
  }
}
