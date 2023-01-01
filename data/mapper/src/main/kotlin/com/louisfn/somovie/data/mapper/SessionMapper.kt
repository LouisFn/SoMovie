package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.domain.model.Account
import com.louisfn.somovie.domain.model.Session
import javax.inject.Inject

class SessionMapper @Inject constructor() {

    fun mapToDomain(sessionData: SessionData) = Session(
        sessionId = sessionData.sessionId,
        languageIso639 = sessionData.languageIso639,
        account = sessionData.account?.let {
            Account(
                id = it.id,
                name = it.name,
                username = it.username
            )
        }
    )

    fun mapToData(session: Session) = SessionData(
        sessionId = session.sessionId,
        languageIso639 = session.languageIso639,
        account = session.account?.let {
            SessionData.Account(
                id = it.id,
                name = it.name,
                username = it.username
            )
        }
    )
}
