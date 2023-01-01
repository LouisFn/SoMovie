package com.louisfn.somovie.domain.usecase.authentication

import com.louisfn.somovie.data.repository.SessionRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) : SuspendUseCase<Unit, Unit>() {

    override suspend fun execute(parameter: Unit) {
        sessionRepository.updateSession {
            it.copy(sessionId = null, account = null)
        }
    }
}
