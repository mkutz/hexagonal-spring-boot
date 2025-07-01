package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article

import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.domain.article.ArticleBuilder.Companion.anArticle
import io.github.mkutz.hexagonalspringboot.hexagonalspringboot.application.ports.driven.ArticleGetterStub
import java.util.UUID.randomUUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArticleServiceTest {

  private val articleGetter = ArticleGetterStub()
  private val articlesService = ArticleService(articleGetter)

  @Test
  fun getArticle() {
    val existingArticle = articleGetter.addArticle(anArticle().build())

    assertThat(articlesService.getArticle(existingArticle.id)).isEqualTo(existingArticle)
  }

  @Test
  fun `getArticle unknown article ID`() {
    assertThat(articlesService.getArticle(randomUUID().toString())).isNull()
  }
}
