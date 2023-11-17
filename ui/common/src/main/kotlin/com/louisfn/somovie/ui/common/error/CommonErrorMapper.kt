package com.louisfn.somovie.ui.common.error

import android.content.Context
import com.louisfn.somovie.domain.exception.NoNetworkException
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import com.louisfn.somovie.ui.common.R as commonR

class CommonErrorMapper @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun map(e: Throwable): Error =
        when (e) {
            is NoNetworkException ->
                SimpleMessageError(context.getString(commonR.string.common_error_no_network))
            else -> {
                Timber.e(e)
                SimpleMessageError(context.getString(commonR.string.common_error_unexpected))
            }
        }
}
