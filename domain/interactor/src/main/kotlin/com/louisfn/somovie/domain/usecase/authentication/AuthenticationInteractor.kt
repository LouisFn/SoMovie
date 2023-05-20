package com.louisfn.somovie.domain.usecase.authentication

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.repository.AccountRepository
import com.louisfn.somovie.data.repository.AuthenticationRepository
import com.louisfn.somovie.data.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val sessionRepository: SessionRepository,
    private val accountRepository: AccountRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    @AnyThread
    suspend fun logIn(requestToken: String) = defaultDispatcher {
        val sessionId = authenticationRepository.createNewSession(requestToken)
        sessionRepository.updateSession { it.copy(sessionId = sessionId) }

        val account = accountRepository.getAccount()
        sessionRepository.updateSession { it.copy(account = account) }
    }

    @AnyThread
    suspend fun logOut() {
        sessionRepository.updateSession {
            it.copy(sessionId = null, account = null)
        }
    }

    @AnyThread
    fun isLoggedIn(): Flow<Boolean> =
        sessionRepository.sessionChanges()
            .map { it.account != null }

    @AnyThread
    suspend fun getRequestToken(): String = authenticationRepository.getRequestToken()
}
