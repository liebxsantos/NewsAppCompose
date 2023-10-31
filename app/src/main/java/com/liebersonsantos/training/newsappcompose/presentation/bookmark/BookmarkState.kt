package com.liebersonsantos.training.newsappcompose.presentation.bookmark

import com.liebersonsantos.training.newsappcompose.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)