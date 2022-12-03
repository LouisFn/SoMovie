package com.louisfn.somovie.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

abstract class PagingUseCase<in P, R : Any> {

    operator fun invoke(parameters: P): Flow<PagingData<R>> =
        execute(parameters)

    protected abstract fun execute(parameter: P): Flow<PagingData<R>>
}
