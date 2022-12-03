package com.louisfn.somovie.domain.usecase.authentication

import com.louisfn.somovie.domain.repository.AccountRepository
import com.louisfn.somovie.domain.repository.AuthenticationRepository
import com.louisfn.somovie.domain.repository.SessionRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val accountRepository: AccountRepository,
    private val sessionRepository: SessionRepository
) : SuspendUseCase<String, Unit>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(requestToken: String) {
        val sessionId = authenticationRepository.createNewSession(requestToken)
        sessionRepository.updateSession { it.copy(sessionId = sessionId) }

        val account = accountRepository.getAccount()
        sessionRepository.updateSession { it.copy(account = account) }
    }
}
