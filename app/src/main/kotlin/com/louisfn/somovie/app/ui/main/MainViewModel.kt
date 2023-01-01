package com.louisfn.somovie.app.ui.main

import androidx.annotation.AnyThread
import androidx.lifecycle.viewModelScope
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.core.logger.Logger
import com.louisfn.somovie.domain.usecase.startup.RefreshConfigurationUseCase
import com.louisfn.somovie.ui.common.LoadingManager
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.base.NoneAction
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import com.louisfn.somovie.ui.common.model.ImmutableList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val errorsDispatcher: ErrorsDispatcher,
    private val refreshConfigurationUseCase: RefreshConfigurationUseCase
) : BaseViewModel<NoneAction>(defaultDispatcher) {

    private val loadingManager = LoadingManager()

    val uiState: StateFlow<MainUiState> =
        loadingManager
            .isLoadingChanges()
            .map(::createMainUiState)
            .flowOn(defaultDispatcher)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MainUiState.Loading
            )

    val errors = errorsDispatcher
        .errorChanges()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ImmutableList()
        )

    init {
        viewModelScope.launch {
            refreshAppData()
        }
    }

    override fun onCleared() {
        errorsDispatcher.cancel()
        super.onCleared()
    }

    @AnyThread
    private fun createMainUiState(isRefreshing: Boolean) =
        if (isRefreshing) MainUiState.Loading
        else MainUiState.Content

    @AnyThread
    private suspend fun refreshAppData() {
        loadingManager.start()
        refreshConfiguration()
        loadingManager.stop()
    }

    @AnyThread
    private suspend fun refreshConfiguration() {
        try {
            refreshConfigurationUseCase(Unit)
        } catch (e: Exception) {
            Logger.e(e)
        }
    }
}
