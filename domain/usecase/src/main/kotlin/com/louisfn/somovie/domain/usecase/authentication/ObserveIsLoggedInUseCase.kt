package com.louisfn.somovie.domain.usecase.authentication

import com.louisfn.somovie.data.repository.SessionRepository
import com.louisfn.somovie.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveIsLoggedInUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) : FlowUseCase<Unit, Boolean>() {

    override fun execute(parameter: Unit): Flow<Boolean> =
        sessionRepository.sessionChanges()
            .map { it.account != null }
}
