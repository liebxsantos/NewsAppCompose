package com.liebersonsantos.training.newsappcompose.data.remote.dto

import com.liebersonsantos.training.newsappcompose.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
