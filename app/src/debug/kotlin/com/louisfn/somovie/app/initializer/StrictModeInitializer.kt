package com.louisfn.somovie.app.initializer

import android.os.Build
import android.os.StrictMode
import android.os.strictmode.Violation
import androidx.annotation.RequiresApi
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.core.logger.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import javax.inject.Inject

internal class StrictModeInitializer @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : AppInitializer {

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            enableStrictMode()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLogError(STACKTRACE_WHITELIST_THREAD)
                .build(),
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLogError(STACKTRACE_WHITELIST_VM)
                .build(),
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun StrictMode.ThreadPolicy.Builder.penaltyLogError(whitelist: List<String>): StrictMode.ThreadPolicy.Builder =
        detectAll()
            .penaltyListener(defaultDispatcher.asExecutor()) { violation ->
                if (shouldLog(whitelist, violation)) {
                    Logger.e(violation)
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    fun StrictMode.VmPolicy.Builder.penaltyLogError(whitelist: List<String>): StrictMode.VmPolicy.Builder =
        detectAll()
            .penaltyListener(defaultDispatcher.asExecutor()) { violation ->
                if (shouldLog(whitelist, violation)) {
                    Logger.e(violation)
                }
            }

    private fun shouldLog(whitelist: List<String>, violation: Violation): Boolean {
        val stackTrace = violation.stackTraceToString()
        whitelist.forEach {
            if (stackTrace.contains(it)) {
                return false
            }
        }
        return true
    }

    companion object {
        private val STACKTRACE_WHITELIST_VM = listOf(
            "android.os.strictmode.UntaggedSocketViolation",
        )
        private val STACKTRACE_WHITELIST_THREAD = listOf(
            "com.louisfn.somovie.app.initializer.FlipperInitializer.onCreate",
        )
    }
}
