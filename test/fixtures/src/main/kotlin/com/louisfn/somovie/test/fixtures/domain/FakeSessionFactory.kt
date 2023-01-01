package com.louisfn.somovie.test.fixtures.domain

import com.louisfn.somovie.domain.model.Account
import com.louisfn.somovie.domain.model.Session

object FakeSessionFactory {

    val default = Session(
        sessionId = "sessionId",
        languageIso639 = "en",
        account = Account(
            id = 10,
            name = "Louis",
            username = "LouisFn"
        )
    )

    val empty = Session(
        sessionId = null,
        languageIso639 = null,
        account = null
    )
}
