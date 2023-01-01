package com.louisfn.somovie.test.fixtures.feature.login

import com.louisfn.somovie.feature.login.LogInManager
import com.louisfn.somovie.feature.login.LogInState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeLogInManager : LogInManager {

    override val state: StateFlow<LogInState> = MutableStateFlow(LogInState.Idle)

    override fun init(scope: CoroutineScope) {
    }

    override fun reset() {
    }

    override fun start() {
    }

    override fun onApproved() {
    }

    override fun onDenied() {
    }
}
