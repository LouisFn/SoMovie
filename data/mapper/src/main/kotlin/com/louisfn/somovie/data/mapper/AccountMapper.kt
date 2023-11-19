package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.network.response.AccountResponse
import com.louisfn.somovie.domain.model.Account
import javax.inject.Inject

class AccountMapper @Inject constructor() {

    //region Map response to domain

    fun mapToDomain(response: AccountResponse) = Account(
        id = response.id,
        name = response.name,
        username = response.username,
    )

    //endregion
}
