package com.louisfn.somovie.domain.usecase.account

import com.louisfn.somovie.domain.model.Account
import com.louisfn.somovie.domain.repository.SessionRepository
import com.louisfn.somovie.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveAccountUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) : FlowUseCase<Unit, Account?>() {

    override fun execute(parameter: Unit): Flow<Account?> =
        sessionRepository
            .sessionChanges()
            .map { it.account }
}
