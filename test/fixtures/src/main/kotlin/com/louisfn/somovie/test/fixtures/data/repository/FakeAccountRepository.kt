package com.louisfn.somovie.test.fixtures.data.repository

import com.louisfn.somovie.data.repository.AccountRepository
import com.louisfn.somovie.domain.model.Account

class FakeAccountRepository : AccountRepository {

    override suspend fun getAccount(): Account {
        TODO("Not yet implemented")
    }
}
