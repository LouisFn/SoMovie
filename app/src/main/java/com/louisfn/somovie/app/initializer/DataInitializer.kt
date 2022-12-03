package com.louisfn.somovie.app.initializer

import androidx.annotation.UiThread
import com.louisfn.somovie.common.annotation.ApplicationScope
import com.louisfn.somovie.common.logger.Logger
import com.louisfn.somovie.domain.usecase.startup.UpdateLanguageUseCase
import com.louisfn.somovie.ui.common.navigation.AppRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DataInitializer @Inject constructor(
    private val appRouter: AppRouter,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    @ApplicationScope private val applicationScope: CoroutineScope
) : AppInitializer {

    override fun onForeground() {
        applicationScope.launch {
            updateLanguage()
        }
    }

    @UiThread
    private suspend fun updateLanguage() {
        try {
            if (updateLanguageUseCase(Unit)) {
                relaunchApp()
            }
        } catch (e: Exception) {
            Logger.e(e)
        }
    }

    @UiThread
    private fun relaunchApp() {
        appRouter.relaunchApp()
    }
}
