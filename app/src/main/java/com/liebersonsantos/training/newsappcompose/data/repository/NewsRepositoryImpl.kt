package com.liebersonsantos.training.newsappcompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.liebersonsantos.training.newsappcompose.data.local.NewsDao
import com.liebersonsantos.training.newsappcompose.data.remote.NewsPagingSource
import com.liebersonsantos.training.newsappcompose.data.remote.SearchNewsPagingSource
import com.liebersonsantos.training.newsappcompose.data.remote.dto.NewsApi
import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val dao: NewsDao
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {
        dao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        dao.delete(article)
    }

    override fun selectArticles(): Flow<List<Article>> {
        return dao.getArticles()
    }

    override suspend fun selectArticle(url: String): Article? {
        return dao.getArticle(url)
    }
}