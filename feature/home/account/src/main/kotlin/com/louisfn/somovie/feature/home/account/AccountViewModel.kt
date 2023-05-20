package com.louisfn.somovie.feature.home.account

import androidx.annotation.AnyThread
import androidx.lifecycle.viewModelScope
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.domain.model.Account
import com.louisfn.somovie.domain.usecase.account.AccountInteractor
import com.louisfn.somovie.domain.usecase.authentication.AuthenticationInteractor
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.base.NoneAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountViewModel @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val accountInteractor: AccountInteractor,
    private val authenticationInteractor: AuthenticationInteractor
) : BaseViewModel<NoneAction>(defaultDispatcher) {

    val uiState: StateFlow<AccountUiState> =
        accountInteractor.accountChanges()
            .map(::createAccountState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AccountUiState.None
            )

    @AnyThread
    private fun createAccountState(account: Account?): AccountUiState =
        when {
            account != null -> AccountUiState.LoggedIn(username = account.username)
            else -> AccountUiState.Disconnected
        }

    @AnyThread
    fun logOut() {
        viewModelScope.launch {
            authenticationInteractor.logOut()
        }
    }
}
