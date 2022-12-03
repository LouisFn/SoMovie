package com.louisfn.somovie.domain.usecase.authentication

import com.louisfn.somovie.domain.repository.SessionRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) : SuspendUseCase<Unit, Boolean>() {

    override suspend fun execute(parameter: Unit): Boolean =
        sessionRepository.getSession().account != null
}
