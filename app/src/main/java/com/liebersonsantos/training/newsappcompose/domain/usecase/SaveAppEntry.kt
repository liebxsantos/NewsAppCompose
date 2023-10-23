package com.liebersonsantos.training.newsappcompose.domain.usecase

import com.liebersonsantos.training.newsappcompose.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}