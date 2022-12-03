package com.louisfn.somovie.domain.usecase

import androidx.annotation.AnyThread
import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in P, R> {

    @AnyThread
    operator fun invoke(parameters: P): Flow<R> =
        execute(parameters)

    @AnyThread
    protected abstract fun execute(parameter: P): Flow<R>
}
