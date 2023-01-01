package com.louisfn.somovie.feature.login

import android.net.Uri
import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.usecase.authentication.GetRequestTokenUseCase
import com.louisfn.somovie.domain.usecase.authentication.LogInUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LogInManager {
    val state: StateFlow<LogInState>

    @AnyThread
    fun init(scope: CoroutineScope)

    @AnyThread
    fun reset()

    @AnyThread
    fun start()

    @AnyThread
    fun onApproved()

    @AnyThread
    fun onDenied()
}

class DefaultLogInManager @Inject constructor(
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val logInUseCase: LogInUseCase
) : LogInManager {

    private lateinit var scope: CoroutineScope
    private var currentRequestToken: String? = null

    private val _state = MutableStateFlow<LogInState>(LogInState.Idle)
    override val state: StateFlow<LogInState> = _state

    override fun init(scope: CoroutineScope) {
        this.scope = scope
    }

    override fun reset() {
        currentRequestToken = null
        _state.value = LogInState.Idle
    }

    override fun start() {
        check(state.value == LogInState.Idle) {
            "A log in flow is already started. You should call reset() before starting a new one"
        }
        _state.value = LogInState.Loading
        scope.launch {
            try {
                _state.value = LogInState.WebView(generateUri())
            } catch (e: Exception) {
                _state.value = LogInState.Failed(e)
            }
        }
    }

    override fun onApproved() {
        scope.launch {
            logIn()
        }
    }

    override fun onDenied() {
        scope.launch {
            _state.value = LogInState.Idle
        }
    }

    private suspend fun logIn() {
        try {
            val token = checkNotNull(currentRequestToken) {
                "currentRequestToken should not be null"
            }
            logInUseCase(token)
            _state.value = LogInState.Succeed
        } catch (e: Exception) {
            _state.value = LogInState.Failed(e)
        }
    }

    private suspend fun generateUri(): Uri {
        currentRequestToken = getRequestTokenUseCase(Unit)
        return Uri.parse(LogInConfig.URL)
            .buildUpon()
            .appendPath(currentRequestToken)
            .build()
    }
}
