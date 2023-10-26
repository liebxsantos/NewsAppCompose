package com.liebersonsantos.training.newsappcompose.di

import android.app.Application
import com.liebersonsantos.training.newsappcompose.BuildConfig
import com.liebersonsantos.training.newsappcompose.data.manager.LocalUserManagerImpl
import com.liebersonsantos.training.newsappcompose.data.remote.dto.NewsApi
import com.liebersonsantos.training.newsappcompose.data.repository.NewsRepositoryImpl
import com.liebersonsantos.training.newsappcompose.domain.manager.LocalUserManager
import com.liebersonsantos.training.newsappcompose.domain.repository.NewsRepository
import com.liebersonsantos.training.newsappcompose.domain.usecase.appentry.AppEntryUseCase
import com.liebersonsantos.training.newsappcompose.domain.usecase.appentry.ReadAppEntry
import com.liebersonsantos.training.newsappcompose.domain.usecase.appentry.SaveAppEntry
import com.liebersonsantos.training.newsappcompose.domain.usecase.news.GetNews
import com.liebersonsantos.training.newsappcompose.domain.usecase.news.NewsUseCase
import com.liebersonsantos.training.newsappcompose.domain.usecase.news.SearchNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCase(localUserManager: LocalUserManager) =
        AppEntryUseCase(
            readAppEntry = ReadAppEntry(localeUserManager = localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager = localUserManager)
        )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase {
        return NewsUseCase(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository)
        )
    }
}