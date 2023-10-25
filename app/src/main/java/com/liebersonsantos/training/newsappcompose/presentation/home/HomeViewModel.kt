package com.liebersonsantos.training.newsappcompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.liebersonsantos.training.newsappcompose.domain.usecase.news.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    val news = newsUseCase.getNews(
        sources = listOf("bbc-news", "abc-news")
    ).cachedIn(viewModelScope)
}