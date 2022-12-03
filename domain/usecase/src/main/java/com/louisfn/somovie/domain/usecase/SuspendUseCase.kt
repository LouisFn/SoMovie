package com.louisfn.somovie.domain.usecase

import androidx.annotation.AnyThread

abstract class SuspendUseCase<in P, R> {

    @AnyThread
    suspend operator fun invoke(parameters: P): R = execute(parameters)

    @AnyThread
    abstract suspend fun execute(parameter: P): R
}
