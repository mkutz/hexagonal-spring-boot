package io.github.mkutz.hexagonalspringboot.hexagonalspringboot.adapters.driven.http.articledata

import java.lang.RuntimeException

class ArticleDataRetrievalException(cause: Throwable) :
  RuntimeException("Failed to retrieve article data", cause)
