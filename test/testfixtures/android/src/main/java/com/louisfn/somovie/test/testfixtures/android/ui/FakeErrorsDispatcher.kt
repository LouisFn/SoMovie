package com.louisfn.somovie.test.testfixtures.android.ui

import com.louisfn.somovie.ui.common.error.Error
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import com.louisfn.somovie.ui.common.model.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeErrorsDispatcher : ErrorsDispatcher {

    private val errors = MutableStateFlow<ImmutableList<FakeError>>(ImmutableList())

    override fun dispatch(throwable: Throwable) {
        errors.update { it + FakeError(throwable) }
    }

    override fun dispatch(message: String) {
        errors.update { it + FakeError(RuntimeException(message)) }
    }

    override fun cancel() {
        errors.value = ImmutableList()
    }

    override fun errorChanges(): Flow<ImmutableList<FakeError>> = errors

    fun hasError(): Boolean = errors.value.isNotEmpty()

    fun hasError(message: String): Boolean = errors.value.any { it.throwable.message == message }

    fun hasError(e: Throwable): Boolean = errors.value.any { it.throwable::class == e::class }

    class FakeError(
        val throwable: Throwable,
        override val duration: Long = 0
    ) : Error
}
