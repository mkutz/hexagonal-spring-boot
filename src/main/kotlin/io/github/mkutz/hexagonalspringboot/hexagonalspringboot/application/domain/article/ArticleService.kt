package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ToGetArticle
import org.springframework.stereotype.Service

@Service
class ArticleService(private val articleGetter: ToGetArticle) {

  fun getArticle(id: String) = articleGetter.getArticle(id)
}
