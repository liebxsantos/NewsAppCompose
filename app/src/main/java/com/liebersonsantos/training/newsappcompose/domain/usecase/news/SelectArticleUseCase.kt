package com.liebersonsantos.training.newsappcompose.domain.usecase.news

import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.repository.NewsRepository

class SelectArticleUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(url: String): Article? {
        return repository.selectArticle(url)
    }
}