package com.louisfn.somovie.domain.usecase.account

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.repository.SessionRepository
import com.louisfn.somovie.domain.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountInteractor @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    @AnyThread
    fun accountChanges(): Flow<Account?> = sessionRepository
        .sessionChanges()
        .map { it.account }

    @AnyThread
    suspend fun isLoggedIn(): Boolean = sessionRepository.getSession().account != null
}
