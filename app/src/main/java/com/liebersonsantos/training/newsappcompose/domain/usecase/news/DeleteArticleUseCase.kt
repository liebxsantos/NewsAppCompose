package com.liebersonsantos.training.newsappcompose.domain.usecase.news

import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.repository.NewsRepository

class DeleteArticleUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.deleteArticle(article)
    }
}