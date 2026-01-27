package io.github.mkutz.order.adapter.out.persistence

import io.github.mkutz.order.application.article.Article
import io.github.mkutz.order.application.article.Money
import java.util.Currency

object ArticlePersistenceMapper {

  fun toDomain(entity: ArticleEntity): Article {
    return Article(
      id = Article.Id(entity.id),
      name = entity.name,
      price = Money(entity.priceAmount, Currency.getInstance(entity.priceCurrency)),
    )
  }

  fun toEntity(article: Article): ArticleEntity {
    return ArticleEntity(
      id = article.id.value,
      name = article.name,
      priceAmount = article.price.amount,
      priceCurrency = article.price.currency.currencyCode,
    )
  }
}
