package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.Article

interface ToGetArticle {

  fun getArticle(articleId: String): Article?
}
