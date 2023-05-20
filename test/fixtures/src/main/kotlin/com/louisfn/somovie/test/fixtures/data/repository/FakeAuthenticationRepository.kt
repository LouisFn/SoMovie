package com.louisfn.somovie.test.fixtures.data.repository

import com.louisfn.somovie.data.repository.AuthenticationRepository

class FakeAuthenticationRepository : AuthenticationRepository {

    override suspend fun getRequestToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun createNewSession(requestToken: String): String {
        TODO("Not yet implemented")
    }
}
