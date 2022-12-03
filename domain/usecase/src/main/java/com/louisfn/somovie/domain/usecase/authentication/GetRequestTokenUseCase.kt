package com.louisfn.somovie.domain.usecase.authentication

import com.louisfn.somovie.domain.repository.AuthenticationRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import javax.inject.Inject

class GetRequestTokenUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SuspendUseCase<Unit, String>() {

    override suspend fun execute(parameter: Unit): String =
        authenticationRepository.getRequestToken()
}
