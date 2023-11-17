package com.louisfn.somovie.feature.login

import androidx.annotation.AnyThread
import androidx.lifecycle.viewModelScope
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.base.NoneAction
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val errorsDispatcher: ErrorsDispatcher,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    logInManager: LogInManager,
) : BaseViewModel<NoneAction>(defaultDispatcher), LogInManager by logInManager {

    override val state: StateFlow<LogInState> =
        logInManager.state
            .onEach(::onState)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                LogInState.Idle,
            )

    init {
        init(viewModelScope)
    }

    @AnyThread
    private fun onState(state: LogInState) {
        when (state) {
            is LogInState.Failed -> whenFailed(state.exception)
            else -> Unit
        }
    }

    @AnyThread
    private fun whenFailed(e: Exception) {
        errorsDispatcher.dispatch(e)
        reset()
    }
}
