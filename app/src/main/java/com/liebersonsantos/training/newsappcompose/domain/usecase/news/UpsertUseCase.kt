package com.liebersonsantos.training.newsappcompose.domain.usecase.news

import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.repository.NewsRepository

class UpsertUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.upsertArticle(article)
    }
}