package com.liebersonsantos.training.newsappcompose.domain.usecase.news

import com.liebersonsantos.training.newsappcompose.data.local.NewsDao
import com.liebersonsantos.training.newsappcompose.domain.model.Article

class DeleteArticleUseCase(
    private val dao: NewsDao
) {
    suspend operator fun invoke(article: Article) {
        dao.delete(article)
    }
}