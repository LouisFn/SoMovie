package com.louisfn.somovie.domain.usecase.startup

import com.louisfn.somovie.data.repository.AppRepository
import com.louisfn.somovie.data.repository.SessionRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import java.util.*
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val sessionRepository: SessionRepository
) : SuspendUseCase<Unit, Boolean>() {

    override suspend fun execute(parameter: Unit): Boolean {
        val localeLanguage = Locale.getDefault().language
        if (localeLanguage != sessionRepository.getSession().languageIso639) {
            sessionRepository.updateSession { it.copy(languageIso639 = localeLanguage) }
            appRepository.clearDatabase()
            return true
        }
        return false
    }
}
