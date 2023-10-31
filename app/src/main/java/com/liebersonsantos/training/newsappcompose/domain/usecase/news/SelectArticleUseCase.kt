package com.liebersonsantos.training.newsappcompose.domain.usecase.news

import com.liebersonsantos.training.newsappcompose.data.local.NewsDao
import com.liebersonsantos.training.newsappcompose.domain.model.Article
import kotlinx.coroutines.flow.Flow

class SelectArticleUseCase(
    private val dao: NewsDao
) {
    operator fun invoke(): Flow<List<Article>> {
       return dao.getArticles()
    }
}