package com.liebersonsantos.training.newsappcompose.di

import android.app.Application
import com.liebersonsantos.training.newsappcompose.data.manager.LocalUserManagerImpl
import com.liebersonsantos.training.newsappcompose.domain.manager.LocalUserManager
import com.liebersonsantos.training.newsappcompose.domain.usecase.AppEntryUseCase
import com.liebersonsantos.training.newsappcompose.domain.usecase.ReadAppEntry
import com.liebersonsantos.training.newsappcompose.domain.usecase.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}