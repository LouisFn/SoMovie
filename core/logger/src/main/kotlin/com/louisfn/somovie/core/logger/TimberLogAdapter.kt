package com.louisfn.somovie.core.logger

import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

internal class TimberLogAdapter @Inject constructor() : LogAdapter {

    override fun setup() {
        Timber.plant(DebugTree())
    }

    override fun v(message: String, vararg args: Any?) {
        Timber.v(message, *args)
    }

    override fun v(t: Throwable, message: String, vararg args: Any?) {
        Timber.v(t, message, *args)
    }

    override fun v(t: Throwable) {
        Timber.v(t)
    }

    override fun d(message: String, vararg args: Any?) {
        Timber.d(message, *args)
    }

    override fun d(t: Throwable, message: String, vararg args: Any?) {
        Timber.d(t, message, *args)
    }

    override fun d(t: Throwable) {
        Timber.d(t)
    }

    override fun i(message: String, vararg args: Any?) {
        Timber.i(message, *args)
    }

    override fun i(t: Throwable, message: String, vararg args: Any?) {
        Timber.i(t, message, *args)
    }

    override fun i(t: Throwable) {
        Timber.i(t)
    }

    override fun w(message: String, vararg args: Any?) {
        Timber.w(message, *args)
    }

    override fun w(t: Throwable, message: String, vararg args: Any?) {
        Timber.w(t, message, *args)
    }

    override fun w(t: Throwable) {
        Timber.w(t)
    }

    override fun e(message: String, vararg args: Any?) {
        Timber.e(message, *args)
    }

    override fun e(t: Throwable, message: String, vararg args: Any?) {
        Timber.e(t, message, *args)
    }

    override fun e(t: Throwable) {
        Timber.e(t)
    }

    override fun wtf(message: String, vararg args: Any?) {
        Timber.wtf(message, *args)
    }

    override fun wtf(t: Throwable, message: String, vararg args: Any?) {
        Timber.wtf(t, message, *args)
    }

    override fun wtf(t: Throwable) {
        Timber.wtf(t)
    }

    /**
     * Source: https://github.com/chrisbanes/tivi/blob/main/base-android/src/main/java/app/tivi/util/TiviLogger.kt
     */
    private class DebugTree : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            super.log(priority, createClassTag(), message, t)
        }

        @Suppress("ThrowingExceptionsWithoutMessageOrCause")
        private fun createClassTag(): String {
            val stackTrace = Throwable().stackTrace
            check(stackTrace.size > CALL_STACK_INDEX) {
                "Synthetic stacktrace didn't have enough elements: are you using proguard?"
            }
            var tag = stackTrace[CALL_STACK_INDEX].className
            val m = ANONYMOUS_CLASS.matcher(tag)
            if (m.find()) {
                tag = m.replaceAll("")
            }

            return tag.substring(tag.lastIndexOf('.') + 1)
        }

        companion object {
            private const val CALL_STACK_INDEX = 7
            private val ANONYMOUS_CLASS by lazy { Pattern.compile("(\\$\\d+)+$") }
        }
    }
}
