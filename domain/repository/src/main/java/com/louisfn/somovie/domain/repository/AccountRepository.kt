package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.model.Account

interface AccountRepository {

    @AnyThread
    suspend fun getAccount(): Account
}
