package com.louisfn.somovie.feature.login

import android.net.Uri
import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.usecase.authentication.AuthenticationInteractor
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
    fun onApprove()

    @AnyThread
    fun onDeny()
}

class DefaultLogInManager @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
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

    override fun onApprove() {
        scope.launch {
            logIn()
        }
    }

    override fun onDeny() {
        scope.launch {
            _state.value = LogInState.Idle
        }
    }

    private suspend fun logIn() {
        try {
            val token = checkNotNull(currentRequestToken) {
                "currentRequestToken should not be null"
            }
            authenticationInteractor.logIn(token)
            _state.value = LogInState.Succeed
        } catch (e: Exception) {
            _state.value = LogInState.Failed(e)
        }
    }

    private suspend fun generateUri(): Uri {
        currentRequestToken = authenticationInteractor.getRequestToken()
        return Uri.parse(LogInConfig.URL)
            .buildUpon()
            .appendPath(currentRequestToken)
            .build()
    }
}
