package com.louisfn.somovie.test.testfixtures.android.data.datastore

import com.louisfn.somovie.data.datastore.model.SessionData

object FakeSessionDataFactory {

    val default = SessionData(
        sessionId = "sessionId",
        languageIso639 = "en",
        account = SessionData.Account(
            id = 10,
            name = "name",
            username = "louis"
        )
    )
}
