package com.louisfn.somovie.domain.usecase.global

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.repository.AppRepository
import com.louisfn.somovie.data.repository.SessionRepository
import java.util.Locale
import javax.inject.Inject

class AppLanguageInteractor @Inject constructor(
    private val appRepository: AppRepository,
    private val sessionRepository: SessionRepository
) {

    /**
     * @return true if local language has been changed
     */
    @AnyThread
    suspend fun refresh(): Boolean {
        val localeLanguage = Locale.getDefault().language
        if (localeLanguage != sessionRepository.getSession().languageIso639) {
            sessionRepository.updateSession { it.copy(languageIso639 = localeLanguage) }
            appRepository.clearDatabase()
            return true
        }
        return false
    }
}
