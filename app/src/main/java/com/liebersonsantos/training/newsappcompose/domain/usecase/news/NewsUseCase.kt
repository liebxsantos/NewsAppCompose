package com.liebersonsantos.training.newsappcompose.domain.usecase.news

data class NewsUseCase(
    val getNews: GetNews,
    val searchNews: SearchNews,
    val selectArticleUseCase: SelectArticleUseCase,
    val deleteArticleUseCase: DeleteArticleUseCase,
    val upsertUseCase: UpsertUseCase
)
