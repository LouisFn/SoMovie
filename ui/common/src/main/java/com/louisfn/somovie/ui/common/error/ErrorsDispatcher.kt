package com.louisfn.somovie.ui.common.error

import androidx.annotation.AnyThread
import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.common.extension.cancelChildren
import com.louisfn.somovie.ui.common.model.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface ErrorsDispatcher {

    @AnyThread
    fun dispatch(throwable: Throwable)

    @AnyThread
    fun dispatch(message: String)

    @AnyThread
    fun cancel()

    @AnyThread
    fun errorChanges(): Flow<ImmutableList<Error>>
}

@Singleton
class DefaultErrorsDispatcher @Inject constructor(
    private val commonErrorMapper: CommonErrorMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ErrorsDispatcher {
    private val scope = CoroutineScope(defaultDispatcher)

    private val _errors = MutableStateFlow<ImmutableList<Error>>(ImmutableList())

    override fun errorChanges(): Flow<ImmutableList<Error>> = _errors

    override fun dispatch(throwable: Throwable) {
        dispatch(commonErrorMapper.map(throwable))
    }

    override fun dispatch(message: String) {
        dispatch(SimpleMessageError(message))
    }

    override fun cancel() {
        _errors.value = ImmutableList()
        scope.cancelChildren()
    }

    @AnyThread
    private fun dispatch(error: Error) =
        scope.launch {
            _errors.update {
                if (!it.contains(error)) ImmutableList(it + error)
                else it
            }

            delay(error.duration)

            _errors.update { ImmutableList(it - error) }
        }
}
