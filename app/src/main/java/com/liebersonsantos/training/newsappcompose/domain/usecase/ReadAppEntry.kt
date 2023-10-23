package com.liebersonsantos.training.newsappcompose.domain.usecase

import com.liebersonsantos.training.newsappcompose.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localeUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return localeUserManager.readAppEntry()
    }
}