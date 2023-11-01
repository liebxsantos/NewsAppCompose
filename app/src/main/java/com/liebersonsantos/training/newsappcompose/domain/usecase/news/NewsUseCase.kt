package com.liebersonsantos.training.newsappcompose.domain.usecase.news

data class NewsUseCase(
    val getNews: GetNews,
    val searchNews: SearchNews,
    val selectArticlesUseCase: SelectArticlesUseCase,
    val deleteArticleUseCase: DeleteArticleUseCase,
    val upsertUseCase: UpsertUseCase,
    val selectArticleUseCase: SelectArticleUseCase
)
