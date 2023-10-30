package com.liebersonsantos.training.newsappcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liebersonsantos.training.newsappcompose.domain.usecase.appentry.AppEntryUseCase
import com.liebersonsantos.training.newsappcompose.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

const val DELAY = 300L

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCase: AppEntryUseCase
): ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        appEntryUseCase.readAppEntry().onEach {
            startDestination = if (it) {
                Route.NewsNavigation.route
            } else {
                Route.AppStartNavigation.route
            }

            delay(DELAY)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}