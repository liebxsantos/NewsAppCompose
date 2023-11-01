package com.liebersonsantos.training.newsappcompose.presentation.detail

import com.liebersonsantos.training.newsappcompose.domain.model.Article

sealed class DetailEvent {
    data class UpsertDeleteArticle(val article: Article) : DetailEvent()
    object RemoveSideEffect : DetailEvent()

}