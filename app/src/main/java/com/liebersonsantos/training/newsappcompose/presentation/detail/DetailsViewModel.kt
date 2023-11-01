package com.liebersonsantos.training.newsappcompose.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.usecase.news.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: NewsUseCase
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = useCase.selectArticleUseCase(event.article.url)
                    if (article == null){
                        upsertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                }
            }

            is DetailEvent.RemoveSideEffect -> { sideEffect = null }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        useCase.deleteArticleUseCase(article)
        sideEffect = "Article Deleted"
    }

    private suspend fun upsertArticle(article: Article) {
        useCase.upsertUseCase(article = article)
        sideEffect = "Article Saved"
    }
}