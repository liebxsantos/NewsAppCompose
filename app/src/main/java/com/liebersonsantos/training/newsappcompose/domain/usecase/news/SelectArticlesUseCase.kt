package com.liebersonsantos.training.newsappcompose.domain.usecase.news

import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticlesUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
       return repository.selectArticles()
    }
}