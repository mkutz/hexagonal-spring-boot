package io.github.mkutz.order.application.port.out

import io.github.mkutz.order.application.article.Article

interface ToLoadArticles {
  fun loadArticle(articleId: Article.Id): Article?

  fun loadArticles(articleIds: List<Article.Id>): List<Article>
}
